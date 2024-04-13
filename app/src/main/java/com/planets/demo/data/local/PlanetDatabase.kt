package com.planets.demo.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [PlanetEntity::class], version = 1, exportSchema = false)
abstract class PlanetDatabase : RoomDatabase() {
    abstract val dao: PlanetDao
}