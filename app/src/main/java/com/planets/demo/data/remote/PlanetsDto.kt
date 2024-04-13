package com.planets.demo.data.remote

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class PlanetsResponse(
    @Expose @SerializedName("count") val count: Int? = null,
    @Expose @SerializedName("next") val next: String? = null,
    @Expose @SerializedName("previous") val previous: String? = null,
    @Expose @SerializedName("results") val planetsList: ArrayList<PlanetsDto> = arrayListOf()
) : Serializable

data class PlanetsDto(
    @SerializedName("name") @Expose val name: String? = null,
    @SerializedName("rotation_period") @Expose val rotationPeriod: String? = null,
    @SerializedName("orbital_period") @Expose val orbitalPeriod: String? = null,
    @SerializedName("diameter") @Expose val diameter: String? = null,
    @SerializedName("climate") @Expose val climate: String? = null,
    @SerializedName("gravity") @Expose val gravity: String? = null,
    @SerializedName("terrain") @Expose val terrain: String? = null,
    @SerializedName("surface_water") @Expose val surfaceWater: String? = null,
    @SerializedName("population") @Expose val population: String? = null,
    @SerializedName("residents") @Expose val residents: ArrayList<String>? = null,
    @SerializedName("films") @Expose val films: ArrayList<String>? = null,
    @SerializedName("created") @Expose val created: String? = null,
    @SerializedName("edited") @Expose val edited: String? = null,
    @SerializedName("url") @Expose val url: String? = null
) : Serializable
