package com.planets.demo.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface PlanetDao {

    @Upsert
    suspend fun upsertAll(planets: List<PlanetEntity>)

    @Query("SELECT * FROM tb_planet")
    fun pagingSource(): PagingSource<Int, PlanetEntity>

    @Query("SELECT * FROM tb_planet WHERE id = :planetId")
    fun getPlanetById(planetId: Int): Flow<PlanetEntity>

    @Query("DELETE FROM tb_planet")
    suspend fun clearAll()
}