package org.example.repository

import org.example.database.MongoConnector
import org.example.entity.GpsRecord

class GpsDataRepository(connector: MongoConnector) : Repository<GpsRecord>(
    connector.database.getCollection("gps_data", GpsRecord::class.java)
)