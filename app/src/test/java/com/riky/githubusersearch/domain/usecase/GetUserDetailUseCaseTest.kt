package com.riky.githubusersearch.domain.usecase

import com.riky.githubusersearch.domain.model.UserDetail
import com.riky.githubusersearch.domain.repository.UserRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class GetUserDetailUseCaseTest {
    private lateinit var repo: UserRepository
    private lateinit var useCase: GetUserDetailUseCase

    @Before
    fun setup() {
        repo = mockk()
        useCase = GetUserDetailUseCase(repo)
    }

    @Test
    fun `get user detail returns correct detail`() = runTest {
        val dummyDetail = UserDetail(
            id = 1,
            username = "testuser",
            avatarUrl = "url",
            bio = "this is bio"
        )
        coEvery { repo.getUserDetail("testuser") } returns dummyDetail

        val result = useCase("testuser")
        assertEquals(dummyDetail, result)
    }
}
