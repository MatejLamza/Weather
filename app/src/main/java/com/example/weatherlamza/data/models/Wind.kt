package com.example.weatherlamza.data.models

import com.google.gson.annotations.SerializedName

data class Wind(
    @SerializedName("deg")
    val degrees: Int,

    @SerializedName("gust")
    val gust: Double,

    @SerializedName("speed")
    val speed: Double
)
