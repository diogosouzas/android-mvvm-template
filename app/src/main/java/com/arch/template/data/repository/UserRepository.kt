package com.arch.template.data.repository

import com.arch.template.core.util.Result
import com.arch.template.data.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun getUsersStream(): Flow<Result<List<User>>>
    fun getUserByIdStream(id: Long): Flow<Result<User?>>
    suspend fun syncUsers(): Result<Unit>
    suspend fun createUser(user: User): Result<User>
    suspend fun updateUser(user: User): Result<User>
    suspend fun deleteUser(id: Long): Result<Unit>
}
