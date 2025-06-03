package org.example.mapping

import org.bson.types.ObjectId
import org.example.dto.GpsBatchRequest
import org.example.entity.GpsRecord

class GpsMapper {
    fun gpsBatchRequestToListOfGpsRecord(gpsBatchRequest: GpsBatchRequest): List<GpsRecord> {
        return gpsBatchRequest.locations.map {
            GpsRecord(timestamp = it.timestamp,
                userId = ObjectId(gpsBatchRequest.userId),
                latitude = it.latitude,
                longitude = it.longitude,
                accuracy = it.accuracy,
                altitude = it.altitude,
                speed = it.speed,
                bearing = it.bearing)
        }
    }
}