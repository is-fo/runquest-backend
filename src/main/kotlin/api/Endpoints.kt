package org.example.api

import io.javalin.Javalin
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import org.example.auth.UserHandler
import org.example.auth.util.TokenUtil
import org.example.repository.GpsDataRepository

class Endpoints(userHandler: UserHandler, gpsDataRepository: GpsDataRepository) {
    init {

        val app = Javalin.create()
        val coroutineScope = CoroutineScope(Dispatchers.IO)

        AuthEndpoints(app, coroutineScope, userHandler)
        RunEndpoints(app, coroutineScope, gpsDataRepository)

        app.beforeMatched {
            val path = it.path()
            if (path.startsWith("/auth")) return@beforeMatched

            if (path.startsWith("/test")) return@beforeMatched //temporär bypass

            val authHeader = it.header("Authorization")
            if (authHeader == null) {
                it.status(401).result("Missing Authorization header").skipRemainingHandlers()
                return@beforeMatched
            }
            val token = authHeader.replace("Bearer ", "")

            try {
                val tokenUtil = TokenUtil()
                val userId = tokenUtil.getUserIdFromToken(token)
                it.attribute("userId", userId)
            } catch (e: Exception) {
                it.status(401).result("Invalid or expired token").skipRemainingHandlers()
                e.printStackTrace()
            }
        }
        app.start(7070)
    }
}