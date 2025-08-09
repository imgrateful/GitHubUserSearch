package com.riky.githubusersearch.fake

import com.riky.githubusersearch.domain.model.User
import com.riky.githubusersearch.domain.model.UserDetail
import com.riky.githubusersearch.domain.repository.UserRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FakeUserRepository @Inject constructor() : UserRepository {
    var searchResult: List<User> = emptyList()
    var detail: UserDetail? = null

    override suspend fun searchUsers(query: String): List<User> = searchResult
    override suspend fun getUserDetail(username: String): UserDetail =
        detail ?: error("FakeUserRepository.detail not set")
}
