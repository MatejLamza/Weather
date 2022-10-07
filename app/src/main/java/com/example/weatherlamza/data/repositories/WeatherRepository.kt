package com.example.weatherlamza.data.repositories

import com.example.jetweatherapp.data.model.Coordinates
import com.example.jetweatherapp.data.model.Location
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {

    fun getWeatherForCoordinates(lat: Double, lon: Double): Flow<Location>

    fun getCoordinates(cityName: String): Flow<Coordinates>

    suspend fun getWeather(cityName: String): Flow<Location>
}
