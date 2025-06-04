package org.example.repository

import com.mongodb.client.model.Filters.eq
import com.mongodb.client.model.Updates.set
import com.mongodb.kotlin.client.coroutine.MongoCollection
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.toList
import org.bson.types.ObjectId

abstract class Repository<T : Any>(private val collection: MongoCollection<T>) {

    suspend fun insertOne(entity: T): T {
        collection.insertOne(entity);
        return entity
    }

    suspend fun insertMany(entities: List<T>) {
        collection.insertMany(entities)
    }

    suspend fun findAll(): List<T> {
        return collection.find().toList()
    }

    suspend fun findById(id: ObjectId): T? {
        return collection.find(eq("_id", id)).firstOrNull()
    }

    suspend fun deleteById(id: ObjectId): Boolean {
        return collection.deleteOne(eq("_id", id)).deletedCount > 0
    }

    suspend fun findByField(fieldName: String, value:Any): List<T> {
        return collection.find(eq(fieldName, value)).toList()
    }

    suspend fun updateFieldById(id: ObjectId, fieldName: String, value:Any): Boolean {
        return collection.updateOne(eq("_id", id), set(fieldName, value)).modifiedCount > 0
    }
}

