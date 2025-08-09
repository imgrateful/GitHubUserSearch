package com.riky.githubusersearch.data.repository

import com.riky.githubusersearch.data.api.GitHubApiService
import com.riky.githubusersearch.data.db.UserDao
import com.riky.githubusersearch.data.db.UserEntity
import com.riky.githubusersearch.data.model.UserDetailResponse
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class UserRepositoryImplTest {

    private lateinit var api: GitHubApiService
    private lateinit var dao: UserDao
    private lateinit var repo: UserRepositoryImpl

    @Before
    fun setup() {
        api = mockk()
        dao = mockk(relaxed = true)
        repo = UserRepositoryImpl(api, dao)
    }

    @Test
    fun `getUserDetail - API success returns API data and caches`() = runTest {
        val username = "rikyaf"

        val apiDetail = UserDetailResponse(
            id = 1,
            login = username,
            avatarUrl = "https://avatars.githubusercontent.com/u/1",
            bio = "api bio",
            name = "Mr. Google",
            company = "GitHub",
            htmlUrl = "https://github.com/rikyaf",
        )

        coEvery { api.getUserDetail(username) } returns apiDetail

        val result = repo.getUserDetail(username)

        assertEquals(username, result.username)
        assertEquals("api bio", result.bio)       // <- data dari API
        coVerify(exactly = 1) { api.getUserDetail(username) }
        coVerify(exactly = 1) { dao.insertUsers(any()) } // cached
    }

    @Test
    fun `getUserDetail - API error falls back to DB`() = runTest {
        val username = "rikyaf"

        val dbDetail = UserEntity(
            id = 1,
            username = username,
            avatarUrl = "https://avatars.githubusercontent.com/u/1",
            bio = "db bio",
            name = "Mr. Google",
            company = "Cached Inc",
            htmlUrl = "https://github.com/rikyaf",
        )

        coEvery { api.getUserDetail(username) } throws RuntimeException("network error")
        coEvery { dao.getUserDetail(username) } returns dbDetail

        val result = repo.getUserDetail(username)

        assertEquals(username, result.username)
        assertEquals("db bio", result.bio)        // <- fallback dari DB
        coVerify(exactly = 1) { dao.getUserDetail(username) }
    }
}