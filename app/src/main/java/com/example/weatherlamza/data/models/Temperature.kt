package com.example.jetweatherapp.data.model

import com.google.gson.annotations.SerializedName

data class Temperature(
    @SerializedName("temp")
    val temperature: Double,

    @SerializedName("feels_like")
    val feelsLike: Double,

    @SerializedName("temp_min")
    val tempMin: Double,

    @SerializedName("temp_max")
    val tempMax: Double,

    @SerializedName("pressure")
    val pressure: Double,

    @SerializedName("humidity")
    val humidity: Double,
)
