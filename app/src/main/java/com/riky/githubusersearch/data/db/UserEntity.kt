package com.riky.githubusersearch.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "github_user")
data class UserEntity(
    @PrimaryKey val id: Int,
    val username: String,
    val avatarUrl: String,
    val bio: String?
)
