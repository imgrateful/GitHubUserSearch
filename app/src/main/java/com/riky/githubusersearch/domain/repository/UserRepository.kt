package com.riky.githubusersearch.domain.repository

import com.riky.githubusersearch.domain.model.User
import com.riky.githubusersearch.domain.model.UserDetail

interface UserRepository {
    suspend fun searchUsers(query: String): List<User>
    suspend fun getUserDetail(username: String): UserDetail
}
