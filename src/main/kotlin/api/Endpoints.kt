package org.example.api

import io.javalin.Javalin
import org.example.auth.UserHandler
import org.example.auth.util.TokenUtil

class Endpoints(userHandler: UserHandler) {
    init {
        val app = Javalin.create()

        AuthEndpoints(app, userHandler)
        RunEndpoints(app)

        app.beforeMatched {
            val path = it.path()
            if (path.startsWith("/auth")) return@beforeMatched

            val authHeader = it.header("Authorization")
            if (authHeader == null) {
                it.status(401).result("Missing Authorization header")
                return@beforeMatched
            }
            val token = authHeader.replace("Bearer ", "")

            try {
                val tokenUtil = TokenUtil()
                val userId = tokenUtil.getUserIdFromToken(token)
                it.attribute("userId", userId)
            } catch (e: Exception) {
                it.status(401).result("Invalid or expired token")
                e.printStackTrace()
            }
        }
        app.start(7070)
    }
}