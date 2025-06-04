package org.example.database

import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings
import com.mongodb.MongoException
import com.mongodb.ServerApi
import com.mongodb.ServerApiVersion
import com.mongodb.kotlin.client.coroutine.MongoClient
import com.mongodb.kotlin.client.coroutine.MongoDatabase
import kotlinx.coroutines.runBlocking
import org.bson.BsonInt64
import org.bson.Document

class MongoConnector(uri: String) {

    private val mongoClient: MongoClient = MongoClient.create(
        MongoClientSettings.builder()
            .applyConnectionString(ConnectionString(uri))
            .serverApi(ServerApi.builder().version(ServerApiVersion.V1).build())
            .build()
    )

    val database: MongoDatabase = mongoClient.getDatabase("runquest").also {
        try {
            runBlocking {
                it.runCommand(Document("ping", BsonInt64(1)))
                println("Pinged your deployment. You successfully connected to MongoDB!")
            }
        } catch (me: MongoException) {
            System.err.println(me)
        }
    }

    init {
        Runtime.getRuntime().addShutdownHook(Thread {
            println("Shutting down MongoClient")
            mongoClient.close()
        })
    }
}

