package com.example.weatherlamza.data.repositories.impl

import com.example.jetweatherapp.data.model.Coordinates
import com.example.jetweatherapp.data.model.Location
import com.example.weatherlamza.data.network.WeatherAPI
import com.example.weatherlamza.data.repositories.WeatherRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext


/**
 * @param coroutineDispatcher is Context on which coroutines are being executed.
 * Default is [IO] which is optimized for this type of work.
 * Dispatchers are injected thorugh constructor because they are easy to replace in unit and
 * instrumentation tests with a [TestDispatcher]. (should implement kotlinx-coroutines-test library)
 */

class WeatherRepositoryImpl(
    private val api: WeatherAPI,
    private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO
) : WeatherRepository {
    override fun getWeatherForCoordinates(lat: Double, lon: Double): Flow<Location> = flow {
        emit(api.getWeatherForCoordinates(lat, lon))
    }.flowOn(coroutineDispatcher)


    override fun getCoordinates(cityName: String): Flow<Coordinates> = flow {
        emit(api.getLocationCoordinatesByName(cityName)[0])
    }.flowOn(coroutineDispatcher)


    override suspend fun getWeather(cityName: String): Flow<Location> =
        withContext(coroutineDispatcher) {
            val coordinates = getCoordinates(cityName).first()
            getWeatherForCoordinates(coordinates.lat, coordinates.lon)
        }

}
