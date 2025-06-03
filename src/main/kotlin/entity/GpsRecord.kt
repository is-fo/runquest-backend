package org.example.entity

import kotlinx.datetime.Instant
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import org.bson.codecs.kotlinx.ObjectIdSerializer
import org.bson.types.ObjectId
import org.example.serialisers.InstantAsBsonDateTimeSerializer

@OptIn(ExperimentalSerializationApi::class)
@Serializable
data class GpsRecord(
    @Serializable(with = InstantAsBsonDateTimeSerializer::class)
    val timestamp: Instant,
    @Serializable(with = ObjectIdSerializer::class)
    val userId: ObjectId,
    val latitude: Double,
    val longitude: Double,
    val accuracy: Float? = null,
    val altitude: Float? = null,
    val speed: Float? = null,
    val bearing: Float? = null
)
