package org.example.api

import io.javalin.Javalin
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.future.future
import org.example.repository.GpsDataRepository
import kotlinx.serialization.json.Json
import org.example.dto.GpsBatchRequest
import org.example.mapping.GpsMapper

class RunEndpoints(private val app: Javalin, private val scope: CoroutineScope, private val gpsDataRepository: GpsDataRepository) {
    init {
        testAuth()
        testRun()
    }

    fun testAuth() {
        app.post("/test") {
            try {
                val userId = it.attribute<String>("userId")
                it.result(userId.toString())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun testRun() {
        app.post("/test/run") {
            try {
                val body = it.body()
                val request = Json.decodeFromString<GpsBatchRequest>(body)
                val attributeUserId = it.attribute<String>("userId")
                if (request.userId != attributeUserId) {
                    throw IllegalAccessException("Mismatched userId: body=${request.userId}, attribute=$attributeUserId")
                }
                val gpsRecords = GpsMapper().gpsBatchRequestToListOfGpsRecord(request)

                it.future {
                    scope.future {
                        gpsDataRepository.insertMany(gpsRecords)
                        it.status(201)
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                it.status(500)
            }
        }
    }
}