package org.example.entity

import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.bson.types.ObjectId

@Serializable
data class User(
    @SerialName("_id")
    @Contextual
    val id: ObjectId = ObjectId(),
    val username: String,
    val hashedPassword: String,
    val email: String,
    val role: String,
    val avatarUrl: String,
)
