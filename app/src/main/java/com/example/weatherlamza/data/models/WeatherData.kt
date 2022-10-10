package com.example.weatherlamza.data.models

import com.google.gson.annotations.SerializedName

data class WeatherData(
    @SerializedName("dt")
    val dt: Int,

    @SerializedName("dt_txt")
    val dtText: String,

    @SerializedName("main")
    val temperature: Temperature,

    @SerializedName("weather")
    val weather: List<Weather>
)
