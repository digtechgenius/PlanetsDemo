package com.planets.demo.data.mappers

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.planets.demo.data.local.PlanetEntity
import com.planets.demo.data.remote.PlanetsDto
import com.planets.demo.domain.Planet
import java.io.UnsupportedEncodingException

fun PlanetsDto.toPlanetEntity(): PlanetEntity {
    val decodedFilms = films?.map { it.decodeUrl() } ?: emptyList()
    val decodedResidents = residents?.map { it.decodeUrl() } ?: emptyList()

    return PlanetEntity(
        id = extractIdFromUrl(url ?: ""),
        name = name ?: "",
        rotationPeriod = rotationPeriod ?: "",
        orbitalPeriod = orbitalPeriod ?: "",
        diameter = diameter ?: "",
        climate = climate ?: "",
        gravity = gravity ?: "",
        terrain = terrain ?: "",
        surfaceWater = surfaceWater ?: "",
        population = population ?: "",
        films = decodedFilms,
        residents = decodedResidents

    )
}

// Extension function to decode URL
fun String.decodeUrl(): String {
    return try {
        java.net.URLDecoder.decode(this, "UTF-8")
    } catch (e: UnsupportedEncodingException) {
        this // Return original string if decoding fails
    }
}

private val urlIdRegex = """(\d+)""".toRegex()

private fun extractIdFromUrl(url: String): Int {
    val matchResult = urlIdRegex.find(url)
    return matchResult?.value?.toIntOrNull()
        ?: 1 // Assumption id is never null. As per REST principle
}

fun PlanetEntity.toPlanet(): Planet {
    return Planet(
        id = id,
        name = name,
        rotationPeriod = rotationPeriod,
        orbitalPeriod = orbitalPeriod,
        diameter = diameter,
        climate = climate,
        gravity = gravity,
        terrain = terrain,
        surfaceWater = surfaceWater,
        population = population,
        films = films,
        residents = residents
    )
}

class Converters {
    @TypeConverter
    fun fromString(value: String): List<String> {
        val listType = object : TypeToken<List<String>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromArrayList(list: List<String>): String {
        return Gson().toJson(list)
    }
}