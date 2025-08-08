package com.riky.githubusersearch.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.riky.githubusersearch.domain.model.UserDetail
import com.riky.githubusersearch.domain.usecase.GetUserDetailUseCase
import com.riky.githubusersearch.presentation.viewmodel.DetailViewModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class DetailViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var useCase: GetUserDetailUseCase
    private lateinit var viewModel: DetailViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
        useCase = mockk()
        viewModel = DetailViewModel(useCase)
    }

    @Test
    fun `loadUser updates userDetail LiveData`() = runTest {
        val dummy = UserDetail(
            id = 1,
            username = "testuser",
            avatarUrl = "url",
            bio = "bio"
        )
        coEvery { useCase("testuser") } returns dummy

        viewModel.loadUser("testuser")
        assertEquals(dummy, viewModel.userDetail.value)
    }
}
