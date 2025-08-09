package com.riky.githubusersearch.external.mapper

import com.riky.githubusersearch.data.db.UserEntity
import com.riky.githubusersearch.data.model.UserDetailResponse
import com.riky.githubusersearch.data.model.UserDto
import com.riky.githubusersearch.domain.model.User
import com.riky.githubusersearch.domain.model.UserDetail

// --- API DTO -> Domain ---
/** Maps API search result DTO to domain [User]. */
fun UserDto.toDomain(): User =
    User(
        id = id,
        username = login,
        avatarUrl = avatarUrl,
        htmlUrl = htmlUrl
    )

/** Maps API detail response DTO to domain [UserDetail]. */
fun UserDetailResponse.toDomain(): UserDetail =
    UserDetail(
        id = id,
        username = login,
        avatarUrl = avatarUrl,
        bio = bio,
        name = name,
        company = company,
        blog = blog,
        location = location,
        email = email,
        twitterUsername = twitterUsername,
        publicRepos = publicRepos,
        publicGists = publicGists,
        followers = followers,
        following = following,
        htmlUrl = htmlUrl,
        createdAt = createdAt,
        updatedAt = updatedAt
    )

// --- Domain -> Entity ---
/** Maps domain [User] to a minimal [UserEntity] for list view caching. */
fun User.toEntityForList(): UserEntity =
    UserEntity(
        id = id,
        username = username,
        avatarUrl = avatarUrl,
        bio = null, // list doesn't need bio
        name = null,
        company = null,
        blog = null,
        location = null,
        email = null,
        twitterUsername = null,
        publicRepos = 0,
        publicGists = 0,
        followers = 0,
        following = 0,
        htmlUrl = htmlUrl,
        createdAt = "",
        updatedAt = ""
    )

/** Maps domain [UserDetail] to a full [UserEntity] for detail caching. */
fun UserDetail.toEntityFull(): UserEntity =
    UserEntity(
        id = id,
        username = username,
        avatarUrl = avatarUrl,
        bio = bio,
        name = name,
        company = company,
        blog = blog,
        location = location,
        email = email,
        twitterUsername = twitterUsername,
        publicRepos = publicRepos,
        publicGists = publicGists,
        followers = followers,
        following = following,
        htmlUrl = htmlUrl,
        createdAt = createdAt,
        updatedAt = updatedAt
    )

// --- Entity -> Domain ---
/** Maps cached [UserEntity] to a minimal [User] for list view. */
fun UserEntity.toDomainList(): User =
    User(
        id = id,
        username = username,
        avatarUrl = avatarUrl,
        htmlUrl = htmlUrl
    )

/** Maps cached [UserEntity] to a full [UserDetail] for detail view. */
fun UserEntity.toDomainDetail(): UserDetail =
    UserDetail(
        id = id,
        username = username,
        avatarUrl = avatarUrl,
        bio = bio,
        name = name,
        company = company,
        blog = blog,
        location = location,
        email = email,
        twitterUsername = twitterUsername,
        publicRepos = publicRepos,
        publicGists = publicGists,
        followers = followers,
        following = following,
        htmlUrl = htmlUrl,
        createdAt = createdAt,
        updatedAt = updatedAt
    )