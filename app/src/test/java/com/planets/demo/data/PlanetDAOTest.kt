package com.planets.demo.data

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.PagingSource
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.planets.demo.TestData
import com.planets.demo.data.local.PlanetDao
import com.planets.demo.data.local.PlanetDatabase
import com.planets.demo.data.local.PlanetEntity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(manifest = Config.NONE)
class PlanetDaoTest {

    // Rule to swap the background executor used by the Architecture Components with a different one
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var planetDao: PlanetDao
    private lateinit var database: PlanetDatabase

    @Before
    fun setup() {
        // Initialize Robolectric
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(context, PlanetDatabase::class.java)
            .allowMainThreadQueries()
            .build()

        planetDao = database.dao
    }

    @After
    fun tearDown() {
        database.close()
    }


    @Test
    fun getAllPlanet() = runBlocking {
        val planet = TestData.planets
        planetDao.upsertAll(planet)
        val retrievedPlanets = planetDao.pagingSource().load(
            PagingSource.LoadParams.Refresh(
                key = null, loadSize = 10, placeholdersEnabled = false
            )
        )
        val data = mutableListOf<PlanetEntity>()

        if (retrievedPlanets is PagingSource.LoadResult.Page) {
            data.addAll(retrievedPlanets.data)
        }
        assertEquals(planet, data)
    }

    @Test
    fun getPlanetByIdTest() = runBlocking {
        val planet = TestData.planets
        planetDao.upsertAll(planet)
        val retrievedPlanet = planetDao.getPlanetById(1).first()

        assertEquals(planet.get(0), retrievedPlanet)
    }

    @Test
    fun clearAllTest() = runBlocking {
        val planet = TestData.planets
        planetDao.upsertAll(planet)
        planetDao.clearAll()
        val retrievedPlanets = planetDao.pagingSource().load(
            PagingSource.LoadParams.Refresh(
                key = null, loadSize = 10, placeholdersEnabled = false
            )
        )
        val data = mutableListOf<PlanetEntity>()

        if (retrievedPlanets is PagingSource.LoadResult.Page) {
            data.addAll(retrievedPlanets.data)
        }
        assertEquals(emptyList<PlanetEntity>(), data)
    }
}

