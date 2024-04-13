package com.planets.demo.data

import android.content.Context
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingConfig
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.planets.demo.TestData
import com.planets.demo.data.local.PlanetDatabase
import com.planets.demo.data.local.PlanetEntity
import com.planets.demo.data.remote.PlanetApi
import com.planets.demo.data.remote.PlanetRemoteMediator
import io.mockk.MockKAnnotations
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import retrofit2.Response
import java.io.IOException

@RunWith(RobolectricTestRunner::class)
@Config(manifest = Config.NONE)
@ExperimentalPagingApi
@ExperimentalCoroutinesApi
class PlanetRemoteMediatorTest {

    private lateinit var planetRemoteMediator: PlanetRemoteMediator
    private lateinit var planetDatabase: PlanetDatabase
    private lateinit var planetApi: PlanetApi

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        val context = ApplicationProvider.getApplicationContext<Context>()
        planetDatabase = Room.inMemoryDatabaseBuilder(context, PlanetDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        planetApi = mockk()
        planetRemoteMediator = PlanetRemoteMediator(planetDatabase, planetApi)
    }

    @After
    fun tearDown() {
        clearAllMocks()
        planetDatabase.close()
    }

    @Test
    fun `load - refresh - success`() {
        runBlocking {
            // Mock planetApi.getPlanets() response
            val planetsResponse = TestData.generateMockPlanetsResponse(1, 10)
            val response = Response.success(planetsResponse)
            coEvery { planetApi.getPlanets(any(), any()) } returns response
            val pagingState = PagingState<Int, PlanetEntity>(
                listOf(),
                null,
                PagingConfig(pageSize = 10),
                10
            )

            // Call the load function with LoadType.REFRESH
            val result = planetRemoteMediator.load(LoadType.REFRESH, pagingState)

            // Assert the result
            assert(result is RemoteMediator.MediatorResult.Success)
        }
    }

    @Test
    fun loadReturnsErrorResultWhenDataFailsToLoad() = runBlocking {

        // Given
        val loadType = LoadType.REFRESH
        val pagingState = PagingState<Int, PlanetEntity>(
            listOf(),
            null,
            PagingConfig(pageSize = 10),
            10
        )
        val errorResponse = TestData.failureResponse()
        coEvery { planetApi.getPlanets(any(), any()) } returns errorResponse

        // Invoke
        val result = planetRemoteMediator.load(loadType, pagingState)

        // Then
        assertTrue(result is RemoteMediator.MediatorResult.Error)
        assertTrue((result as RemoteMediator.MediatorResult.Error).throwable is IOException)
    }


}
