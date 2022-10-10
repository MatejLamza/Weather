package com.example.weatherlamza.data.repositories.impl

import android.util.Log
import com.example.weatherlamza.data.local.dao.WeatherForecastDAO
import com.example.weatherlamza.data.models.Coordinates
import com.example.weatherlamza.data.models.Forecast
import com.example.weatherlamza.data.models.Location
import com.example.weatherlamza.data.network.WeatherAPI
import com.example.weatherlamza.data.repositories.WeatherRepository
import com.example.weatherlamza.utils.extensions.mappers.toEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext
import org.joda.time.LocalDate


/**
 * @param coroutineDispatcher is Context on which coroutines are being executed.
 * Default is [IO] which is optimized for this type of work.
 * Dispatchers are injected thorugh constructor because they are easy to replace in unit and
 * instrumentation tests with a [TestDispatcher]. (should implement kotlinx-coroutines-test library)
 */

class WeatherRepositoryImpl(
    private val api: WeatherAPI,
    private val weatherDB: WeatherForecastDAO,
    private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO
) : WeatherRepository {
    override fun getWeatherForCoordinates(lat: Double, lon: Double): Flow<Location> = flow {
        emit(api.getWeatherForCoordinates(lat, lon))
    }
        .onEach { location -> weatherDB.insertWeatherForecast(location.toEntity()) }
        .flowOn(coroutineDispatcher)

    override fun getCoordinates(cityName: String): Flow<Coordinates> = flow {
        emit(api.getLocationCoordinatesByName(cityName)[0])
    }.flowOn(coroutineDispatcher)

    override suspend fun getWeather(cityName: String): Flow<Location> =
        withContext(coroutineDispatcher) {
            val coordinates = getCoordinates(cityName).first()
            getWeatherForCoordinates(coordinates.lat, coordinates.lon)
        }

    override fun getForecast(lat: Double, lon: Double): Flow<Forecast> = flow {
        val temp = api.getThreeDayForecast(lat, lon)
        val currentDateStr = temp.weatherData[0].dtText.trim().split(" ")[0]
        Log.d("bbb", "getForecast: $currentDateStr")
        var dateNow = LocalDate.parse(currentDateStr)
        val nextDate = dateNow.plusDays(3)

        val mapped = temp.weatherData.filter {
            LocalDate.parse(it.dtText.trim().split(" ")[0]) <= nextDate
        }

        while (dateNow <= nextDate) {
            val maxForThisDay = mapped.filter {
                val currentDate = LocalDate.parse(it.dtText.trim().split(" ")[0])
                currentDate == dateNow
            }.maxBy { it.temperature.tempMax }

            Log.d("bbb", "Max fro the first day: $maxForThisDay ")
            dateNow = dateNow.plusDays(1)
        }



        emit(temp)
    }.flowOn(coroutineDispatcher)

}
