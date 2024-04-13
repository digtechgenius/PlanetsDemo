package com.planets.demo.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.planets.demo.data.mappers.Converters

@Database(entities = [PlanetEntity::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class PlanetDatabase : RoomDatabase() {
    abstract val dao: PlanetDao
}