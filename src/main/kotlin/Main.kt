package org.example

import kotlinx.coroutines.runBlocking
import org.example.api.Endpoints
import org.example.auth.UserHandler
import org.example.database.MongoConnector
import org.example.repository.GpsDataRepository
import org.example.repository.UserRepository

fun main() {

        try {
            val connector = MongoConnector("mongodb+srv://moth:${System.getenv("mothpw")}@dbteknik.stwun.mongodb.net/?retryWrites=true&w=majority&appName=dbteknik")
            val userHandler = UserHandler(UserRepository(connector))
            //TODO inte använda GpsDataRepository direkt i Endpoints
            val gpsDataRepository = GpsDataRepository(connector)
            Endpoints(userHandler, gpsDataRepository)
        } catch (e: Exception) {
            e.printStackTrace()
        }

}