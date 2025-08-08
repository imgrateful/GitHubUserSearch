package com.riky.githubusersearch.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SearchUserResponse(
    @Json(name = "items")
    val items: List<UserDto>
)
