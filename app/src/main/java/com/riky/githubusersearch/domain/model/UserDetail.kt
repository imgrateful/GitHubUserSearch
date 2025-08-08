package com.riky.githubusersearch.domain.model

data class UserDetail(
    val id: Int,
    val username: String,
    val avatarUrl: String,
    val bio: String?
)
