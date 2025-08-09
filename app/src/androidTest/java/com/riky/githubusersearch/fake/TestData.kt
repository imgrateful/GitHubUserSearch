package com.riky.githubusersearch.fake

import com.riky.githubusersearch.domain.model.User
import com.riky.githubusersearch.domain.model.UserDetail

fun user(id: Int, username: String) = User(
    id = id,
    username = username,
    avatarUrl = "https://example.com/$username.png",
    htmlUrl = "https://github.com/$username"
)

fun userDetail(username: String) = UserDetail(
    id = 1,
    username = username,
    avatarUrl = "https://example.com/$username.png",
    name = "Name $username",
    bio = "Bio $username",
    company = "Company",
    blog = "https://blog.example",
    location = "Earth",
    email = "$username@example.com",
    twitterUsername = "tw_$username",
    publicRepos = 10,
    publicGists = 2,
    followers = 100,
    following = 5,
    htmlUrl = "https://github.com/$username",
    createdAt = "2011-01-01T00:00:00Z",
    updatedAt = "2025-01-01T00:00:00Z"
)
