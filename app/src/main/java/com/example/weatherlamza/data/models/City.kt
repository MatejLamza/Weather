package com.example.weatherlamza.data.models

import com.google.gson.annotations.SerializedName

data class City(
    @SerializedName("coord")
    val coordinates: Coordinates,

    @SerializedName("country")
    val country: String,

    @SerializedName("name")
    val name: String,

    @SerializedName("sunrise")
    val sunrise: Int,

    @SerializedName("sunset")
    val sunset: Int,

    )
