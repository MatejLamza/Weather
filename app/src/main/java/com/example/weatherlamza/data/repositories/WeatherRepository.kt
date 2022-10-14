package com.example.weatherlamza.data.repositories

import com.example.weatherlamza.data.models.Coordinates
import com.example.weatherlamza.data.models.Forecast
import com.example.weatherlamza.data.models.Location
import com.example.weatherlamza.data.models.WeatherData
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {

    fun getHourlyForecast(lat: Double, lon: Double): Flow<List<WeatherData>>

    fun getWeatherForCoordinates(lat: Double, lon: Double): Flow<Location>

    fun getCoordinates(cityName: String): Flow<Coordinates>

    suspend fun getWeather(cityName: String): Flow<Location>

    fun getForecast(lat: Double, lon: Double): Flow<Forecast>
}
