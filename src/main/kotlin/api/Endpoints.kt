package org.example.api

import io.javalin.Javalin
import org.example.auth.UserHandler
import org.example.dto.LoginRequest
import org.example.dto.RegisterRequest
import kotlinx.serialization.json.*

class Endpoints(private val userHandler: UserHandler) {
    fun init() {

        val app = Javalin.create {

        }
            .get("/") {
                it.result("Hello Javalin: ${it.ip()}")
            }.start(7070)

        app.post("/auth/register") {
            val body = it.body()
            try {
                val request = Json.decodeFromString<RegisterRequest>(body)
                val success = userHandler.register(request.username, request.password, request.email)
                if (success) it.status(201).result("User registered") else it.status(400).result("User already exists")
            } catch (e: Exception) {
                e.printStackTrace()
                it.status(500)
            }
        }

        app.post("/auth/login") {
            val body = it.body()
            try {
                val request = Json.decodeFromString<LoginRequest>(body)
                val success = userHandler.login(request.usernameOrEmail, request.rawPassword)
                if (success != null) it.status(200).result(success) else it.status(401)
            } catch (e: Exception) {
                e.printStackTrace()
                it.status(500)
            }
        }
    }
}