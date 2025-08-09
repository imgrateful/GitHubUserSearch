package com.riky.githubusersearch.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "github_user")
data class UserEntity(
    @PrimaryKey val id: Int,
    val username: String,
    val avatarUrl: String,
    val bio: String? = null,
    val name: String? = null,
    val company: String? = null,
    val blog: String? = null,
    val location: String? = null,
    val email: String? = null,
    val twitterUsername: String? = null,
    val publicRepos: Int? = null,
    val publicGists: Int? = null,
    val followers: Int? = null,
    val following: Int? = null,
    val htmlUrl: String? = null,
    val createdAt: String? = null,
    val updatedAt: String? = null,
)
