package com.example.weatherlamza.data.models

import com.google.gson.annotations.SerializedName


data class Location(
    @SerializedName("name")
    val name: String,

    @SerializedName("main")
    val temperature: Temperature,

    @SerializedName("weather")
    val weather: List<Weather>,

    @SerializedName("wind")
    val wind: Wind
) {

    val currentWeather = this.weather[0]
}
