package org.example.api

import io.javalin.Javalin
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.future.future
import kotlinx.serialization.json.Json
import org.example.auth.UserHandler
import org.example.dto.LoginRequest
import org.example.dto.RegisterRequest

class AuthEndpoints(private val app: Javalin, private val scope: CoroutineScope, private val userHandler: UserHandler) {

    init {
        login()
        register()
    }

    private fun login() {
        app.post("/auth/login") {
            val body = it.body()
            try {
                val request = Json.decodeFromString<LoginRequest>(body)
                it.future {
                    scope.future {
                        val token = userHandler.login(request.usernameOrEmail, request.rawPassword)
                        if (token != null) it.status(200).result(token) else it.status(401)
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                it.status(500)
            }
        }
    }

    private fun register() {
        app.post("/auth/register") {
            val body = it.body()
            try {
                val request = Json.decodeFromString<RegisterRequest>(body)

                it.future {
                    scope.future {
                        val success = userHandler.register(request.username, request.password, request.email)
                        if (success) it.status(201).result("User registered") else it.status(400)
                            .result("User already exists")
                    }
                }

            } catch (e: Exception) {
                e.printStackTrace()
                it.status(500)
            }
        }
    }
}
