package com.example.weatherlamza.data.network

import com.example.weatherlamza.data.models.Coordinates
import com.example.weatherlamza.data.models.Forecast
import com.example.weatherlamza.data.models.Location
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherAPI {

    @GET("/data/2.5/weather")
    suspend fun getWeatherForCoordinates(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("units") units: String = "metric"
    ): Location

    @GET("/geo/1.0/direct")
    suspend fun getLocationCoordinatesByName(
        @Query("q") city: String,
    ): List<Coordinates>

    @GET("/data/2.5/forecast")
    suspend fun getThreeDayForecast(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("units") units: String = "metric"
    ): Forecast
}
