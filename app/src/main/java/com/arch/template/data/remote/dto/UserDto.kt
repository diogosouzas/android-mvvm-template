package com.arch.template.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import com.arch.template.data.model.User

@Serializable
data class UserDto(
    @SerialName("id")
    val id: Long,
    @SerialName("name")
    val name: String,
    @SerialName("email")
    val email: String,
    @SerialName("avatar")
    val profilePictureUrl: String? = null
)

fun UserDto.toUser(): User {
    return User(
        id = id,
        name = name,
        email = email,
        profilePictureUrl = profilePictureUrl
    )
}

fun User.toUserDto(): UserDto {
    return UserDto(
        id = id,
        name = name,
        email = email,
        profilePictureUrl = profilePictureUrl
    )
}
