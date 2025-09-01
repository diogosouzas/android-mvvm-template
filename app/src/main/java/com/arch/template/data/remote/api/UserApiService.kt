package com.arch.template.data.remote.api

import com.arch.template.data.remote.dto.UserDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface UserApiService {

    @GET("users")
    suspend fun getUsers(): Response<List<UserDto>>

    @GET("users/{id}")
    suspend fun getUserById(@Path("id") id: Long): Response<UserDto>

    @POST("users")
    suspend fun createUser(@Body user: UserDto): Response<UserDto>

    @PUT("users/{id}")
    suspend fun updateUser(
        @Path("id") id: Long,
        @Body user: UserDto
    ): Response<UserDto>

    @DELETE("users/{id}")
    suspend fun deleteUser(@Path("id") id: Long): Response<Unit>
}
