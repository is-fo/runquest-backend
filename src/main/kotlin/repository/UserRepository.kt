package org.example.repository

import com.mongodb.MongoClientSettings
import kotlinx.serialization.ExperimentalSerializationApi
import org.example.database.MongoConnector
import org.bson.codecs.configuration.CodecRegistry
import org.bson.codecs.configuration.CodecRegistries.fromRegistries
import org.bson.codecs.configuration.CodecRegistries.fromProviders
import org.bson.codecs.kotlinx.KotlinSerializerCodecProvider
import org.example.entity.User

@OptIn(ExperimentalSerializationApi::class)
class UserRepository(connector: MongoConnector) : Repository<User>(
//    run {
//        val codecRegistry: CodecRegistry = fromRegistries(
//            MongoClientSettings.getDefaultCodecRegistry(),
//            fromProviders(KotlinSerializerCodecProvider())
//        )
//
//        connector.database.withCodecRegistry(codecRegistry)
//            .getCollection("users", User::class.java)
//    }
    connector.database.getCollection<User>("users", User::class.java)
)
