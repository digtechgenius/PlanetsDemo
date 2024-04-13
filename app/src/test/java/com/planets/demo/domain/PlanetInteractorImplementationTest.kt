package com.planets.demo.domain

import com.planets.demo.TestData
import com.planets.demo.data.local.PlanetDao
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class PlanetInteractorImplementationTest {

    // Mock PlanetDao
    @Mock
    lateinit var planetDao: PlanetDao

    // System under test
    private lateinit var interactor: PlanetInteractorImplementation

    @Before
    fun setup() {
        // Initialize Mockito annotations
        MockitoAnnotations.initMocks(this)

        // Initialize the interactor with the mocked PlanetDao
        interactor = PlanetInteractorImplementation(planetDao)
    }

    @Test
    fun `test getPlanetById`() {


        // Stub the behavior of planetDao.getPlanetById to return a Flow with the mocked data
        `when`(planetDao.getPlanetById(TestData.planetId.toInt())).thenReturn(flowOf(TestData.planetEntity))

        // Call the function under test
        val resultFlow = interactor.getPlanetById(TestData.planetId)

        // Run the test in a blocking manner using runBlocking
        runBlocking {
            // Collect the emitted values from the flow
            val result = resultFlow.toList()

            // Assert that the emitted value is equal to the expected planet
            assertEquals(TestData.expectedPlanet, result.first())
        }
    }
}
