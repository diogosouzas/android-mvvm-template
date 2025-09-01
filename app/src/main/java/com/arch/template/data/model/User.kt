package com.arch.template.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Entity(tableName = "users")
@Serializable
data class User(
    @PrimaryKey
    val id: Long,
    val name: String,
    val email: String,
    val profilePictureUrl: String? = null
)
