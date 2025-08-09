package com.riky.githubusersearch.domain.model

/**
 * Full domain model for a single GitHub user profile.
 * Mirrors GET /users/{username} fields that are commonly useful in UI.
 */
data class UserDetail(
    val id: Int,
    val username: String,
    val avatarUrl: String,

    // Profile basics
    val name: String? = null,
    val bio: String? = null,
    val company: String? = null,
    val blog: String? = null,
    val location: String? = null,
    val email: String? = null,
    val twitterUsername: String? = null,

    // Stats
    val publicRepos: Int? = null,
    val publicGists: Int? = null,
    val followers: Int? = null,
    val following: Int? = null,

    // Links & timestamps
    val htmlUrl: String? = null,
    // ISO-8601 string from API (e.g., 2011-01-25T18:44:36Z).
    val createdAt: String? = null,
    val updatedAt: String? = null
)

