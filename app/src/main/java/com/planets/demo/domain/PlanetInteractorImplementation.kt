package com.planets.demo.domain

import com.planets.demo.data.local.PlanetDao
import com.planets.demo.data.mappers.toPlanet
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PlanetInteractorImplementation @Inject constructor(private val planetDao: PlanetDao) :
    PlanetInteractorInterface {
    fun getPlanetById(planetId: String): Flow<Planet> =
        planetDao.getPlanetById(planetId.toInt()).map { it.toPlanet() }
}