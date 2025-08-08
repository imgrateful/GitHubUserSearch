package com.riky.githubusersearch.data.api

import com.riky.githubusersearch.data.model.SearchUserResponse
import com.riky.githubusersearch.data.model.UserDetailResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GitHubApiService {
    @GET("search/users")
    suspend fun searchUsers(@Query("q") query: String): SearchUserResponse

    @GET("users/{username}")
    suspend fun getUserDetail(@Path("username") username: String): UserDetailResponse
}
