package com.example.weatherlamza.data.repositories.impl

import com.example.weatherlamza.data.local.dao.SearchDAO
import com.example.weatherlamza.data.local.dao.WeatherForecastDAO
import com.example.weatherlamza.data.local.entity.RecentSearchesEntity
import com.example.weatherlamza.data.models.Coordinates
import com.example.weatherlamza.data.models.Forecast
import com.example.weatherlamza.data.models.Location
import com.example.weatherlamza.data.models.WeatherData
import com.example.weatherlamza.data.network.WeatherAPI
import com.example.weatherlamza.data.repositories.WeatherRepository
import com.example.weatherlamza.utils.extensions.currentDate
import com.example.weatherlamza.utils.extensions.currentDateString
import com.example.weatherlamza.utils.extensions.mappers.toDomain
import com.example.weatherlamza.utils.extensions.mappers.toEntity
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.*
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
    private val recentSearchesDAO: SearchDAO,
    private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO
) : WeatherRepository {


    override fun getWeatherForCoordinates(lat: Double, lon: Double): Flow<Location> = flow {
        emit(api.getWeatherForCoordinates(lat, lon))
    }
        .onEach { location -> weatherDB.insertWeatherForecast(location.toEntity()) }
        .catch {
            withContext(coroutineDispatcher) {
                emit(weatherDB.getWeatherForecast().toDomain())
            }
        }
        .flowOn(coroutineDispatcher)

    override fun getCoordinates(cityName: String): Flow<Coordinates> = flow {
        emit(api.getLocationCoordinatesByName(cityName)[0])
    }
        .flowOn(coroutineDispatcher)

    override suspend fun getWeather(cityName: String): Flow<Location> =
        withContext(coroutineDispatcher) {
            val coordinates = getCoordinates(cityName).last()
            getWeatherForCoordinates(coordinates.lat, coordinates.lon)
                .onEach { recentSearchesDAO.insertRecentSearchQuery(RecentSearchesEntity(cityName)) }
                .catch {
                    withContext(coroutineDispatcher) {
                        emit(weatherDB.getWeatherForecast().toDomain())
                    }
                }
                .flowOn(coroutineDispatcher)
        }

    override fun getForecast(lat: Double, lon: Double): Flow<Forecast> = flow {
        val forecast =
            api.getThreeDayForecast(lat, lon)
        emit(forecast.copy(weatherData = getNextThreeDayForecast(forecast)))
    }
        .onEach { weatherDB.insertDailyForecast(it.toEntity()) }
        .catch {
            withContext(coroutineDispatcher) {
                emit(weatherDB.getDailyForecast().toDomain())
            }
        }
        .flowOn(coroutineDispatcher)

    private fun getNextThreeDayForecast(forecast: Forecast): List<WeatherData> {
        val currentDate = forecast.weatherData[0].currentDate
        val targetDate = currentDate.plusDays(3)
        val threeDayForecast = mutableListOf<WeatherData>()

        val filteredList = forecast.weatherData.filter {
            LocalDate.parse(it.currentDateString) <= targetDate
        }

        var dateToday = currentDate

        while (dateToday <= targetDate) {
            val maxForThisDay = filteredList
                .filter { it.currentDate == dateToday }
                .maxBy { it.temperature.tempMax }

            threeDayForecast.add(maxForThisDay)
            dateToday = dateToday.plusDays(1)
        }

        //take last three will exclude today's forecast
        return threeDayForecast.takeLast(3)
    }
}
