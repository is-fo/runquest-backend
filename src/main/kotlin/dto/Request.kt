package org.example.dto

import kotlinx.serialization.Serializable

@Serializable
data class RegisterRequest(
    val username: String,
    val password: String,
    val email: String
)

@Serializable
data class LoginRequest(
    val usernameOrEmail: String,
    val rawPassword: String
)