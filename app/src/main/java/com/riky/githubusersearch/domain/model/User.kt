package com.riky.githubusersearch.domain.model

/**
 * Lightweight domain model for list views (search results).
 * Keep this minimal for performance; use [UserDetail] for full profile info.
 */
data class User(
    val id: Int,
    val username: String,
    val avatarUrl: String,
    // Public profile URL
    val htmlUrl: String? = null
)
