package com.planets.demo.di

import android.content.Context
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.room.Room
import com.planets.demo.data.local.PlanetDao
import com.planets.demo.data.local.PlanetDatabase
import com.planets.demo.data.local.PlanetEntity
import com.planets.demo.data.remote.PlanetApi
import com.planets.demo.data.remote.PlanetRemoteMediator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@OptIn(ExperimentalPagingApi::class)
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providePlanetDatabase(@ApplicationContext context: Context): PlanetDatabase {
        return Room.databaseBuilder(
            context,
            PlanetDatabase::class.java,
            "planets.db"
        ).build()
    }

    @Singleton
    @Provides
    fun providePlanetDao(database: PlanetDatabase): PlanetDao {
        return database.dao
    }

    @Provides
    @Singleton
    fun providePlanetApi(): PlanetApi {
        return Retrofit.Builder()
            .baseUrl(PlanetApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create()
    }

    @Provides
    @Singleton
    fun providePlanetPager(
        planetDb: PlanetDatabase,
        planetApi: PlanetApi
    ): Pager<Int, PlanetEntity> {
        return Pager(
            config = PagingConfig(pageSize = 10),
            remoteMediator = PlanetRemoteMediator(
                planetDb = planetDb,
                planetApi = planetApi
            ),
            pagingSourceFactory = {
                planetDb.dao.pagingSource()
            }
        )
    }
}