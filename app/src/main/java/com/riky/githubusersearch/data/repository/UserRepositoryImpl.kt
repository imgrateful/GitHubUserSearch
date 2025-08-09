package com.riky.githubusersearch.data.repository

import com.riky.githubusersearch.data.api.GitHubApiService
import com.riky.githubusersearch.data.db.UserDao
import com.riky.githubusersearch.domain.model.User
import com.riky.githubusersearch.domain.model.UserDetail
import com.riky.githubusersearch.domain.repository.UserRepository
import com.riky.githubusersearch.external.mapper.toDomain
import com.riky.githubusersearch.external.mapper.toDomainDetail
import com.riky.githubusersearch.external.mapper.toDomainList
import com.riky.githubusersearch.external.mapper.toEntityForList
import com.riky.githubusersearch.external.mapper.toEntityFull
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Implementation of [UserRepository] that retrieves data from the GitHub API
 * and caches it in a local Room database via [UserDao].
 *
 * - Search results are stored with minimal info (list view)
 * - Detailed user info is stored fully after fetching from the API
 * - Falls back to local cache when API requests fail
 */
class UserRepositoryImpl @Inject constructor(
    private val api: GitHubApiService,
    private val dao: UserDao
) : UserRepository {

    /**
     * Searches GitHub users by username.
     * - First tries to fetch results from the API
     * - On success: maps API data to domain model and caches it locally
     * - On failure: returns cached results from local Room DB
     */
    override suspend fun searchUsers(query: String): List<User> = withContext(Dispatchers.IO) {
        if (query.isBlank()) return@withContext emptyList()

        try {
            val res = api.searchUsers(query.trim())
            val users = res.items.map { it.toDomain() }

            // Cache minimal info for list display
            val entities = users.map { it.toEntityForList() }
            dao.insertUsers(entities)

            users
        } catch (e: Exception) {
            // Fallback to local cache search (LIKE %query%)
            dao.searchUser("%${query.trim()}%").map { it.toDomainList() }
        }
    }

    /**
     * Retrieves detailed information for a single GitHub user.
     * - First tries to fetch the latest detail from the API
     * - On success: maps and stores full detail in Room
     * - On failure: returns detail from local cache if available
     */
    override suspend fun getUserDetail(username: String): UserDetail = withContext(Dispatchers.IO) {
        require(username.isNotBlank()) { "username must not be blank" }

        try {
            val detail = api.getUserDetail(username.trim())
            val domain = detail.toDomain()

            dao.insertUsers(listOf(domain.toEntityFull()))
            domain
        } catch (e: Exception) {
            val cached = dao.getUserDetail(username.trim()) ?: throw e
            cached.toDomainDetail()
        }
    }
}