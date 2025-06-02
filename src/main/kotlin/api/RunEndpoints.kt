package org.example.api

import io.javalin.Javalin

class RunEndpoints(private val app: Javalin) {
    init {
        testAuth()
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
}