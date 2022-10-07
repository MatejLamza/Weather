package com.example.jetweatherapp.data.model

import com.google.gson.annotations.SerializedName

data class Coordinates(
    @SerializedName("lat")
    val lat: Double,

    @SerializedName("lon")
    val lon: Double
)
