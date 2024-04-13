package com.planets.demo.data.remote

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PlanetApi {
    @GET("api/planets/")
    suspend fun getPlanets(
        @Query("page") page: Int,
        @Query("per_page") pageCount: Int
    ): Response<PlanetsResponse>
    companion object {
        const val BASE_URL = "https://swapi.dev/"
    }
}