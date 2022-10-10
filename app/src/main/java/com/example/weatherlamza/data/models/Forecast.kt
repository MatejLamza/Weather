package com.example.weatherlamza.data.models

import com.google.gson.annotations.SerializedName

data class Forecast(
    @SerializedName("city")
    val city: City,

    @SerializedName("list")
    val weatherData: List<WeatherData>,
)
