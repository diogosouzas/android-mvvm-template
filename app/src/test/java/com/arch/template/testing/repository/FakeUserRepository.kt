package com.arch.template.testing.repository

import com.arch.template.core.util.Result
import com.arch.template.data.model.User
import com.arch.template.data.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeUserRepository : UserRepository {

    private val users = mutableListOf<User>()
    private var shouldReturnError = false

    fun setShouldReturnError(value: Boolean) {
        shouldReturnError = value
    }

    fun addUsers(vararg user: User) {
        users.addAll(user)
    }

    fun clearUsers() {
        users.clear()
    }

    override fun getUsersStream(): Flow<Result<List<User>>> = flow {
        emit(Result.Loading)
        if (shouldReturnError) {
            emit(Result.Error(Exception("Test error")))
        } else {
            emit(Result.Success(users.toList()))
        }
    }

    override fun getUserByIdStream(id: Long): Flow<Result<User?>> = flow {
        emit(Result.Loading)
        if (shouldReturnError) {
            emit(Result.Error(Exception("Test error")))
        } else {
            val user = users.find { it.id == id }
            emit(Result.Success(user))
        }
    }

    override suspend fun syncUsers(): Result<Unit> {
        return if (shouldReturnError) {
            Result.Error(Exception("Sync failed"))
        } else {
            Result.Success(Unit)
        }
    }

    override suspend fun createUser(user: User): Result<User> {
        return if (shouldReturnError) {
            Result.Error(Exception("Create failed"))
        } else {
            users.add(user)
            Result.Success(user)
        }
    }

    override suspend fun updateUser(user: User): Result<User> {
        return if (shouldReturnError) {
            Result.Error(Exception("Update failed"))
        } else {
            val index = users.indexOfFirst { it.id == user.id }
            if (index != -1) {
                users[index] = user
                Result.Success(user)
            } else {
                Result.Error(Exception("User not found"))
            }
        }
    }

    override suspend fun deleteUser(id: Long): Result<Unit> {
        return if (shouldReturnError) {
            Result.Error(Exception("Delete failed"))
        } else {
            users.removeAll { it.id == id }
            Result.Success(Unit)
        }
    }
}
