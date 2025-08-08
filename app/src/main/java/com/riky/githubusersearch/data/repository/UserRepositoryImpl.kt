package com.riky.githubusersearch.data.repository

import com.riky.githubusersearch.data.api.GitHubApiService
import com.riky.githubusersearch.data.db.UserDao
import com.riky.githubusersearch.data.db.UserEntity
import com.riky.githubusersearch.domain.model.User
import com.riky.githubusersearch.domain.model.UserDetail
import com.riky.githubusersearch.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val api: GitHubApiService,
    private val dao: UserDao
) : UserRepository {
    override suspend fun searchUsers(query: String): List<User> {
        return try {
            val res = api.searchUsers(query)
            val users = res.items.map { User(it.id, it.login, it.avatarUrl) }
            dao.insertUsers(users.map { UserEntity(it.id, it.username, it.avatarUrl, null) })
            users
        } catch (e: Exception) {
            dao.searchUser("%$query%").map { User(it.id, it.username, it.avatarUrl) }
        }
    }
    override suspend fun getUserDetail(username: String): UserDetail {
        return try {
            val detail = api.getUserDetail(username)
            UserDetail(detail.id, detail.login, detail.avatarUrl, detail.bio)
        } catch (e: Exception) {
            dao.getUserDetail(username)?.let {
                UserDetail(it.id, it.username, it.avatarUrl, it.bio)
            } ?: throw e
        }
    }
}
