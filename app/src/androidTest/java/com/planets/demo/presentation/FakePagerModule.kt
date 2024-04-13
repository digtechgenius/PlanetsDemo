package com.planets.demo.presentation

import android.content.Context
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.planets.demo.data.local.PlanetDao
import com.planets.demo.data.local.PlanetDatabase
import com.planets.demo.data.local.PlanetEntity
import com.planets.demo.di.AppModule
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [AppModule::class]
)
@Module
object FakePagerModule {

    @Provides
    @Singleton
    fun providePlanetDb(): PlanetDatabase {
        val context = ApplicationProvider.getApplicationContext<Context>()
        return Room.inMemoryDatabaseBuilder(context, PlanetDatabase::class.java)
            .allowMainThreadQueries()
            .build()
    }

    @Singleton
    @Provides
    fun providePlanetDao(database: PlanetDatabase): PlanetDao {
        return database.dao
    }


    @Provides
    @Singleton
    fun providePager(): Pager<Int, PlanetEntity> {
        val fakeData = TestData.planets

        val pager = Pager(
            config = PagingConfig(pageSize = 10),
            pagingSourceFactory = {
                object : PagingSource<Int, PlanetEntity>() {

                    override fun getRefreshKey(state: PagingState<Int, PlanetEntity>): Int? {
                        return 1
                    }

                    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PlanetEntity> {
                        return LoadResult.Page(fakeData, prevKey = null, nextKey = null)
                    }
                }
            }
        )
        return pager
    }

}


