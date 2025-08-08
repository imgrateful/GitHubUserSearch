package com.riky.githubusersearch.domain.usecase

import com.riky.githubusersearch.domain.model.User
import com.riky.githubusersearch.domain.repository.UserRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class SearchUsersUseCaseTest {
    private lateinit var repo: UserRepository
    private lateinit var useCase: SearchUsersUseCase

    @Before
    fun setup() {
        repo = mockk()
        useCase = SearchUsersUseCase(repo)
    }

    @Test
    fun `search users returns data`() = runTest {
        val dummyUsers = listOf(User(1, "testuser", "url"))
        coEvery { repo.searchUsers("test") } returns dummyUsers

        val result = useCase("test")
        assertEquals(dummyUsers, result)
    }
}
