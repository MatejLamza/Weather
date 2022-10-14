package com.example.weatherlamza.ui.weather

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherlamza.common.state.State
import com.example.weatherlamza.data.models.Forecast
import com.example.weatherlamza.data.models.Location
import com.example.weatherlamza.data.models.WeatherData
import com.example.weatherlamza.data.repositories.WeatherRepository
import com.example.weatherlamza.utils.extensions.launch
import com.example.weatherlamza.utils.extensions.launchWithState
import kotlinx.coroutines.flow.combine

class WeatherViewModel(private val weatherRepo: WeatherRepository) : ViewModel() {

    private var _weather = MutableLiveData<Location>()
    val weather: LiveData<Location> = _weather

    private var _dailyForecast = MutableLiveData<Forecast>()
    val dailyForecast: LiveData<Forecast> = _dailyForecast

    private var _weatherState = MutableLiveData<State>()
    val weatherState: LiveData<State> = _weatherState

    private var _hourlyForecast = MutableLiveData<List<WeatherData>>()
    val hourlyForecast: LiveData<List<WeatherData>> = _hourlyForecast

    init {
        launchWithState(_weatherState) {
            weatherRepo.getWeather("Zagreb").collect { location ->
                _weather.value = location
            }
        }
    }

    private fun getHourlyForecast(location: android.location.Location) {
        launch {
            weatherRepo.getHourlyForecast(location.latitude, location.longitude).collect {
                _hourlyForecast.value = it
            }
        }
    }

    fun getWeather(location: android.location.Location) {
        launchWithState(_weatherState) {
            weatherRepo.getWeatherForCoordinates(location.latitude, location.longitude)
                .combine(
                    weatherRepo.getForecast(location.latitude, location.longitude)
                ) { currentLocation, forecast ->
                    getHourlyForecast(location)
                    return@combine currentLocation to forecast
                }.collect {
                    _weather.value = it.first!!
                    _dailyForecast.value = it.second!!
                }
        }
    }
}

