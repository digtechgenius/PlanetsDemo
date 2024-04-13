package com.planets.demo.data.remote
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.io.Serializable

@JsonClass(generateAdapter = true)
data class PlanetsResponse(
    @Json(name = "count") val count: Int? = null,
    @Json(name = "next") val next: String? = null,
    @Json(name = "previous") val previous: String? = null,
    @Json(name = "results") val planetsList: List<PlanetsDto>? = null
) : Serializable

@JsonClass(generateAdapter = true)
data class PlanetsDto(
    @Json(name = "name") val name: String? = null,
    @Json(name = "rotation_period") val rotationPeriod: String? = null,
    @Json(name = "orbital_period") val orbitalPeriod: String? = null,
    @Json(name = "diameter") val diameter: String? = null,
    @Json(name = "climate") val climate: String? = null,
    @Json(name = "gravity") val gravity: String? = null,
    @Json(name = "terrain") val terrain: String? = null,
    @Json(name = "surface_water") val surfaceWater: String? = null,
    @Json(name = "population") val population: String? = null,
    @Json(name = "residents") val residents: List<String>? = null,
    @Json(name = "films") val films: List<String>? = null,
    @Json(name = "created") val created: String? = null,
    @Json(name = "edited") val edited: String? = null,
    @Json(name = "url") val url: String? = null
) : Serializable
