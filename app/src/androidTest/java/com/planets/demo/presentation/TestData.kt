package com.planets.demo.presentation

import com.planets.demo.data.local.PlanetEntity
import com.planets.demo.data.mappers.toPlanet
import com.planets.demo.data.remote.PlanetsDto
import com.planets.demo.data.remote.PlanetsResponse
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Response

object TestData {

    fun generateMockPlanetsResponse(
        page: Int,
        pageSize: Int,
        emptyList: Boolean = false
    ): PlanetsResponse {
        val planets = if (emptyList) {
            emptyList()
        } else {
            val startIndex = (page - 1) * pageSize
            val endIndex = startIndex + pageSize
            (startIndex until endIndex).map { index ->
                PlanetsDto(
                    name = "Planet $index",
                    rotationPeriod = "$index",
                    orbitalPeriod = "$index",
                    diameter = "$index",
                    climate = "Climate $index",
                    gravity = "Gravity $index",
                    terrain = "Terrain $index",
                    surfaceWater = "$index",
                    population = "$index",
                    created = "Created $index",
                    edited = "Edited $index",
                    url = "https://swapi.dev/api/planets/$index/"
                )
            }
        }
        val planetsResponse = PlanetsResponse(
            count = planets.size,
            next = "nextPageUrl",
            previous = "previousPageUrl",
            planetsList = ArrayList(planets)
        )
        return planetsResponse
    }
    fun failureResponse(): Response<PlanetsResponse> {
        val errorCode = 404 // Specify the desired HTTP error code
        val errorBody = "{\"error\": \"Resource not found\"}" // Sample JSON error body
        val errorResponse = Response.error<PlanetsResponse>(
            errorCode,
            errorBody.toResponseBody("application/json".toMediaTypeOrNull())
        )
        return errorResponse
    }
    fun generateMockPlanetEntities(page: Int, pageSize: Int): List<PlanetEntity> {
        val startIndex = (page - 1) * pageSize
        val endIndex = startIndex + pageSize
        return (startIndex until endIndex).map { index ->
            PlanetEntity(
                id = index.inc(),
                name = "Planet $index",
                rotationPeriod = "$index",
                orbitalPeriod = "$index",
                diameter = "$index",
                climate = "Climate $index",
                gravity = "Gravity $index",
                terrain = "Terrain $index",
                surfaceWater = "$index",
                population = "$index",
                created = "Created $index",
                edited = "Edited $index",
                url = "https://swapi.dev/api/planets/$index/"
            )
        }
    }

    val planets = listOf(
        PlanetEntity(
            id = 1,
            name = "Tatooine",
            rotationPeriod = "23",
            orbitalPeriod = "304",
            diameter = "10465",
            climate = "Arid",
            gravity = "1 standard",
            terrain = "Desert",
            surfaceWater = "1",
            population = "200000",
            films = emptyList(),
            residents = emptyList(),
            created = "2014-12-09T13:50:49.641000Z",
            edited = "2014-12-20T20:58:18.411000Z",
            url = "https://swapi.dev/api/planets/1/"
        ),
        PlanetEntity(
            id = 2,
            name = "Alderaan",
            rotationPeriod = "24",
            orbitalPeriod = "364",
            diameter = "12500",
            climate = "Temperate",
            gravity = "1 standard",
            terrain = "Grasslands, mountains",
            surfaceWater = "40",
            films = emptyList(),
            residents = emptyList(),
            population = "2000000000",
            created = "2014-12-10T11:35:48.479000Z",
            edited = "2014-12-20T20:58:18.420000Z",
            url = "https://swapi.dev/api/planets/2/"
        )
    )

    // Mocked data
    val planetId = "1"
    val planetEntity = PlanetEntity(
        id = 1,
        name = "Tatooine",
        rotationPeriod = "23",
        orbitalPeriod = "304",
        diameter = "10465",
        climate = "Arid",
        gravity = "1 standard",
        terrain = "Desert",
        surfaceWater = "1",
        population = "200000",
        residents = listOf("https://swapi.dev/api/people/1/", "https://swapi.dev/api/people/2/"),
        films = listOf("https://swapi.dev/api/films/1/", "https://swapi.dev/api/films/3/"),
        created = "2014-12-09T13:50:49.641000Z",
        edited = "2014-12-20T20:58:18.411000Z",
        url = "https://swapi.dev/api/planets/1/"
    )
    val expectedPlanet = planetEntity.toPlanet()

}
