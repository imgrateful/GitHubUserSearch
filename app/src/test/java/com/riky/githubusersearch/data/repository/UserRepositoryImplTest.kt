package com.riky.githubusersearch.data.repository

import com.riky.githubusersearch.data.api.GitHubApiService
import com.riky.githubusersearch.data.db.UserDao
import com.riky.githubusersearch.data.db.UserEntity
import com.riky.githubusersearch.data.model.UserDetailResponse
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class UserRepositoryImplTest {

    private lateinit var api: GitHubApiService
    private lateinit var dao: UserDao
    private lateinit var repo: UserRepositoryImpl

    @Before
    fun setup() {
        api = mockk()
        dao = mockk()
        repo = UserRepositoryImpl(api, dao)
    }

    @Test
    fun `getUserDetail returns detail from API and fallback to DB on error`() = runTest {
        val username = "octocat"
        val dummyApiDetail = UserDetailResponse(
            id = 1,
            login = username,
            avatarUrl = "url",
            bio = "api bio"
        )
        val dummyDbDetail = UserEntity(
            id = 1,
            username = username,
            avatarUrl = "url",
            bio = "db bio"
        )

        // Success case: API returns data
        coEvery { api.getUserDetail(username) } returns dummyApiDetail
        coEvery { dao.getUserDetail(username) } returns dummyDbDetail

        val resultApi = repo.getUserDetail(username)
        assertEquals(username, resultApi.username)
        assertEquals("api bio", resultApi.bio)
        coVerify { api.getUserDetail(username) }

        // Fallback case: API throws error, use DB
        coEvery { api.getUserDetail(username) } throws Exception("network error")
        coEvery { dao.getUserDetail(username) } returns dummyDbDetail

        val resultDb = repo.getUserDetail(username)
        assertEquals(username, resultDb.username)
        assertEquals("db bio", resultDb.bio)
        coVerify { dao.getUserDetail(username) }
    }
}
