package org.example

import org.example.api.Endpoints
import org.example.auth.UserHandler
import org.example.database.MongoConnector
import org.example.repository.UserRepository

fun main() {
    val connector = MongoConnector("mongodb://moth:${System.getenv("mothpw")}@localhost/runquest")
    val userHandler = UserHandler(UserRepository(connector))
    Endpoints(userHandler).init()

    println(userHandler.register(
        "john doe",
        "supersecret",
        "hej@gmail.com",
    ))
    println(userHandler.login("john doe", "supersecret"))
    println(userHandler.login("hej@gmail.com", "superwrong"))

}