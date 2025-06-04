package org.example.dto

import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable
import org.example.serializers.UnixTimestampSerializer

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

@Serializable
data class GpsBatchRequest(
    val userId: String,
    val locations: List<LocationData>
)

@Serializable
data class LocationData(
    @Serializable(with = UnixTimestampSerializer::class)
    val timestamp: Instant,
    val latitude: Double,
    val longitude: Double,
    val accuracy: Float? = null,
    val altitude: Float? = null,
    val speed: Float? = null,
    val bearing: Float? = null
)

