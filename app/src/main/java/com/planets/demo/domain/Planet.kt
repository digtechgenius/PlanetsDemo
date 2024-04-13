package com.planets.demo.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Planet(
    val id: Int,
    val name: String? = null,
    val rotationPeriod: String? = null,
    val orbitalPeriod: String? = null,
    val diameter: String? = null,
    val climate: String? = null,
    val gravity: String? = null,
    val terrain: String? = null,
    val surfaceWater: String? = null,
    val population: String? = null,
    val residents: List<String>? = null,
    val films: List<String>? = null,
    val created: String? = null,
    val edited: String? = null,
    val url: String? = null
) : Parcelable
