package com.planets.demo.data

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.planets.demo.TestData
import com.planets.demo.data.remote.PlanetApi
import com.planets.demo.data.remote.PlanetsResponse
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.assertEquals
import org.junit.Test
import retrofit2.Response

class PlanetApiTest {

    private val planetApi = mock<PlanetApi>()

    @Test
    fun `getPlanets returns success response`() {
        // Mock response data
        val planetsResponse = TestData.generateMockPlanetsResponse(1, 10)
        val response = Response.success(planetsResponse)

        // Mock Retrofit call
        runBlocking {
            whenever(planetApi.getPlanets(1, 10)).thenReturn(response)
        }

        // Call the function and assert the response
        val actualResponse = runBlocking { planetApi.getPlanets(1, 10) }
        assertEquals(response, actualResponse)
    }

    @Test
    fun `getPlanets returns error response`() {
        // Mock error response
        val responseBody = "Error".toResponseBody(null)
        val response = Response.error<PlanetsResponse>(400, responseBody)

        // Mock Retrofit call
        runBlocking {
            whenever(planetApi.getPlanets(1, 10)).thenReturn(response)
        }

        // Call the function and assert the response
        val actualResponse = runBlocking { planetApi.getPlanets(1, 10) }
        assertEquals(response, actualResponse)
    }
}