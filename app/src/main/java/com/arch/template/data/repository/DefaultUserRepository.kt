package com.arch.template.data.repository

import com.arch.template.core.util.Result
import com.arch.template.data.local.dao.UserDao
import com.arch.template.data.model.User
import com.arch.template.data.remote.api.UserApiService
import com.arch.template.data.remote.dto.toUser
import com.arch.template.data.remote.dto.toUserDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import timber.log.Timber

class DefaultUserRepository(
    private val userDao: UserDao,
    private val userApiService: UserApiService
) : UserRepository {

    override fun getUsersStream(): Flow<Result<List<User>>> = flow {
        emit(Result.Loading)
        try {
            userDao.getAllUsersStream().map { users ->
                Result.Success(users)
            }.collect { result ->
                emit(result)
            }
        } catch (e: Exception) {
            Timber.e(e, "Error getting users from local database")
            emit(Result.Error(e))
        }
    }

    override fun getUserByIdStream(id: Long): Flow<Result<User?>> = flow {
        emit(Result.Loading)
        try {
            userDao.getUserByIdStream(id).map { user ->
                Result.Success(user)
            }.collect { result ->
                emit(result)
            }
        } catch (e: Exception) {
            Timber.e(e, "Error getting user by id from local database")
            emit(Result.Error(e))
        }
    }

    override suspend fun syncUsers(): Result<Unit> {
        return try {
            val response = userApiService.getUsers()
            if (response.isSuccessful) {
                val users = response.body()?.map { it.toUser() } ?: emptyList()
                userDao.deleteAllUsers()
                userDao.insertUsers(users)
                Result.Success(Unit)
            } else {
                Result.Error(Exception("Network error: ${response.code()}"))
            }
        } catch (e: Exception) {
            Timber.e(e, "Error syncing users")
            Result.Error(e)
        }
    }

    override suspend fun createUser(user: User): Result<User> {
        return try {
            val response = userApiService.createUser(user.toUserDto())
            if (response.isSuccessful) {
                val createdUser = response.body()?.toUser()
                if (createdUser != null) {
                    userDao.insertUser(createdUser)
                    Result.Success(createdUser)
                } else {
                    Result.Error(Exception("Failed to parse created user"))
                }
            } else {
                Result.Error(Exception("Network error: ${response.code()}"))
            }
        } catch (e: Exception) {
            Timber.e(e, "Error creating user")
            Result.Error(e)
        }
    }

    override suspend fun updateUser(user: User): Result<User> {
        return try {
            val response = userApiService.updateUser(user.id, user.toUserDto())
            if (response.isSuccessful) {
                val updatedUser = response.body()?.toUser()
                if (updatedUser != null) {
                    userDao.updateUser(updatedUser)
                    Result.Success(updatedUser)
                } else {
                    Result.Error(Exception("Failed to parse updated user"))
                }
            } else {
                Result.Error(Exception("Network error: ${response.code()}"))
            }
        } catch (e: Exception) {
            Timber.e(e, "Error updating user")
            Result.Error(e)
        }
    }

    override suspend fun deleteUser(id: Long): Result<Unit> {
        return try {
            val response = userApiService.deleteUser(id)
            if (response.isSuccessful) {
                val user = userDao.getUserById(id)
                if (user != null) {
                    userDao.deleteUser(user)
                }
                Result.Success(Unit)
            } else {
                Result.Error(Exception("Network error: ${response.code()}"))
            }
        } catch (e: Exception) {
            Timber.e(e, "Error deleting user")
            Result.Error(e)
        }
    }
}
