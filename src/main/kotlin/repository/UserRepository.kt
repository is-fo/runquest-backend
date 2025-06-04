package org.example.repository

import org.example.database.MongoConnector
import org.example.entity.User

class UserRepository(connector: MongoConnector) : Repository<User>(
    connector.database.getCollection("users", User::class.java)
)
