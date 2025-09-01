package com.arch.template.ui.home

import app.cash.turbine.test
import com.arch.template.core.util.Result
import com.arch.template.data.model.User
import com.arch.template.domain.usecase.UserUseCases
import com.arch.template.domain.usecase.GetUsersUseCase
import com.arch.template.domain.usecase.SyncUsersUseCase
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.koin.core.context.stopKoin
import org.koin.core.context.startKoin
import org.koin.dsl.module

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private val getUsersUseCase = mockk<GetUsersUseCase>()
    private val syncUsersUseCase = mockk<SyncUsersUseCase>()

    private lateinit var viewModel: HomeViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        
        startKoin {
            modules(module {
                factory { getUsersUseCase }
                factory { syncUsersUseCase }
                factory { UserUseCases(get(), get()) }
                factory { HomeViewModel(get()) }
            })
        }
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        stopKoin()
    }

    @Test
    fun `when users are loaded successfully, uiState should contain users`() = runTest {
        // Given
        val users = listOf(
            User(1, "John Doe", "john@example.com"),
            User(2, "Jane Smith", "jane@example.com")
        )
        every { getUsersUseCase() } returns flowOf(Result.Success(users))

        // When
        viewModel = org.koin.core.context.GlobalContext.get().get()
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        viewModel.uiState.test {
            val state = awaitItem()
            state.users shouldBe users
            state.isLoading shouldBe false
            state.errorMessage shouldBe null
        }
    }

    @Test
    fun `when loading users fails, uiState should contain error message`() = runTest {
        // Given
        val errorMessage = "Network error"
        every { getUsersUseCase() } returns flowOf(Result.Error(Exception(errorMessage)))

        // When
        viewModel = org.koin.core.context.GlobalContext.get().get()
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        viewModel.uiState.test {
            val state = awaitItem()
            state.users shouldBe emptyList()
            state.isLoading shouldBe false
            state.errorMessage shouldBe errorMessage
        }
    }

    @Test
    fun `when refresh is called, should call syncUsersUseCase`() = runTest {
        // Given
        every { getUsersUseCase() } returns flowOf(Result.Success(emptyList()))
        coEvery { syncUsersUseCase() } returns Result.Success(Unit)
        viewModel = org.koin.core.context.GlobalContext.get().get()

        // When
        viewModel.onRefresh()
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        viewModel.uiState.test {
            val state = awaitItem()
            state.isRefreshing shouldBe false
        }
    }

    @Test
    fun `when clearError is called, error message should be null`() = runTest {
        // Given
        every { getUsersUseCase() } returns flowOf(Result.Error(Exception("Error")))
        viewModel = org.koin.core.context.GlobalContext.get().get()
        testDispatcher.scheduler.advanceUntilIdle()

        // When
        viewModel.clearError()

        // Then
        viewModel.uiState.test {
            val state = awaitItem()
            state.errorMessage shouldBe null
        }
    }
}
