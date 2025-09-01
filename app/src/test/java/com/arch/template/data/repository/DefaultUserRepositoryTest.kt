package com.arch.template.data.repository

import app.cash.turbine.test
import com.arch.template.core.util.Result
import com.arch.template.data.local.dao.UserDao
import com.arch.template.data.model.User
import com.arch.template.data.remote.api.UserApiService
import com.arch.template.data.remote.dto.UserDto
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Before
import org.junit.Test
import retrofit2.Response

class DefaultUserRepositoryTest {

    private val userDao = mockk<UserDao>(relaxed = true)
    private val userApiService = mockk<UserApiService>()
    private lateinit var repository: DefaultUserRepository

    @Before
    fun setup() {
        repository = DefaultUserRepository(userDao, userApiService)
    }

    @Test
    fun `getUsersStream should return users from local database`() = runTest {
        // Given
        val users = listOf(
            User(1, "John Doe", "john@example.com"),
            User(2, "Jane Smith", "jane@example.com")
        )
        every { userDao.getAllUsersStream() } returns flowOf(users)

        // When & Then
        repository.getUsersStream().test {
            // Skip loading state and check final result
            skipItems(1) // Skip Loading
            val successResult = awaitItem()
            successResult.shouldBeInstanceOf<Result.Success<List<User>>>()
            successResult.data shouldBe users
            awaitComplete()
        }
    }

    @Test
    fun `syncUsers should sync users from API to local database`() = runTest {
        // Given
        val usersDto = listOf(
            UserDto(1, "John Doe", "john@example.com"),
            UserDto(2, "Jane Smith", "jane@example.com")
        )
        coEvery { userApiService.getUsers() } returns Response.success(usersDto)

        // When
        val result = repository.syncUsers()

        // Then
        result.shouldBeInstanceOf<Result.Success<Unit>>()
    }

    @Test
    fun `syncUsers should return error when API call fails`() = runTest {
        // Given
        coEvery { userApiService.getUsers() } returns Response.error(
            404,
            "Not found".toResponseBody()
        )

        // When
        val result = repository.syncUsers()

        // Then
        result.shouldBeInstanceOf<Result.Error>()
    }

    @Test
    fun `createUser should create user via API and save to local database`() = runTest {
        // Given
        val user = User(1, "John Doe", "john@example.com")
        val userDto = UserDto(1, "John Doe", "john@example.com")
        coEvery { userApiService.createUser(any()) } returns Response.success(userDto)

        // When
        val result = repository.createUser(user)

        // Then
        result.shouldBeInstanceOf<Result.Success<User>>()
        result.data shouldBe user
    }
}
