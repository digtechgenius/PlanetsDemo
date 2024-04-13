package com.planets.demo.data.remote

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.planets.demo.data.local.PlanetDatabase
import com.planets.demo.data.local.PlanetEntity
import com.planets.demo.data.mappers.toPlanetEntity
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class PlanetRemoteMediator(
    private val planetDb: PlanetDatabase,
    private val planetApi: PlanetApi
) : RemoteMediator<Int, PlanetEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, PlanetEntity>
    ): MediatorResult {
        return try {
            val loadKey = when (loadType) {
                LoadType.REFRESH -> 1
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> state.lastItemOrNull()?.id?.let { lastItemId ->
                    (lastItemId / state.config.pageSize) + 1
                } ?: 1
            }

            val planetsResponse = planetApi.getPlanets(
                page = loadKey,
                pageCount = state.config.pageSize
            )
            var endOfPaginationReached = true
            if(planetsResponse.isSuccessful){
                val planets = planetsResponse.body()?.planetsList ?: emptyList()
                 endOfPaginationReached = planets.isEmpty()

                planetDb.withTransaction {
                    if (loadType == LoadType.REFRESH) {
                        planetDb.dao.clearAll()
                    }
                    planetDb.dao.upsertAll(planets.map { it.toPlanetEntity() })
                }
            } else{
                throw IOException()
            }
            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }
}