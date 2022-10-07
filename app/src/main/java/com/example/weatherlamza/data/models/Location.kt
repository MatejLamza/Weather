package com.example.jetweatherapp.data.model

import com.google.gson.annotations.SerializedName


data class Location(
    @SerializedName("name")
    val name: String,

    @SerializedName("main")
    val temperature: Temperature,

    @SerializedName("weather")
    val weather: List<Weather>
) {
    val currentWeather = this.weather[0]
}
