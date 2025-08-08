package com.riky.githubusersearch.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.riky.githubusersearch.domain.model.User
import com.riky.githubusersearch.domain.usecase.SearchUsersUseCase
import com.riky.githubusersearch.presentation.viewmodel.SearchViewModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class SearchViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var useCase: SearchUsersUseCase
    private lateinit var viewModel: SearchViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
        useCase = mockk()
        viewModel = SearchViewModel(useCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `search updates users LiveData with result`() = runTest {
        val dummy = listOf(User(1, "testuser", "url"))
        coEvery { useCase("test") } returns dummy

        viewModel.search("test")
        assertEquals(dummy, viewModel.users.value)
    }

    @Test
    fun `search updates users LiveData to empty on error`() = runTest {
        coEvery { useCase("test") } throws RuntimeException("error")

        viewModel.search("test")
        assertEquals(emptyList<User>(), viewModel.users.value)
    }
}
