package com.riky.githubusersearch.integration

import com.riky.githubusersearch.data.api.GitHubApiService
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class GitHubApiIntegrationTest {

    private val api = Retrofit.Builder()
        .baseUrl("https://api.github.com/")
        .addConverterFactory(MoshiConverterFactory.create())
        .build()
        .create(GitHubApiService::class.java)

    @Test
    fun `search users with actual API returns result`() = runTest {
        val result = api.searchUsers("john")
        assertTrue(result.items.isNotEmpty())
        assertNotNull(result.items[0].login)
    }

    @Test
    fun `get user detail with actual API returns correct detail`() = runTest {
        val user = api.getUserDetail("octocat")
        assertEquals("octocat", user.login)
        assertNotNull(user.avatarUrl)
    }
}