package org.example.repository

import com.mongodb.client.MongoCollection
import com.mongodb.client.model.Filters.eq
import com.mongodb.client.model.Updates.set
import org.bson.types.ObjectId

abstract class Repository<T : Any>(private val collection: MongoCollection<T>) {

    fun insertOne(entity: T): T {
        collection.insertOne(entity);
        return entity
    }

    fun insertMany(entities: List<T>) {
        collection.insertMany(entities)
    }

    fun findAll(): List<T> {
        return collection.find().toList()
    }

    fun findById(id: ObjectId): T? {
        return collection.find(eq("_id", id)).firstOrNull()
    }

    fun deleteById(id: ObjectId): Boolean {
        return collection.deleteOne(eq("_id", id)).deletedCount > 0
    }

    fun findByField(fieldName: String, value:Any): List<T> {
        return collection.find(eq(fieldName, value)).toList()
    }

    fun updateFieldById(id: ObjectId, fieldName: String, value:Any): Boolean {
        return collection.updateOne(eq("_id", id), set(fieldName, value)).modifiedCount > 0
    }
}

