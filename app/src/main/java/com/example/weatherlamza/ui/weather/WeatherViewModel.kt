package com.example.weatherlamza.ui.weather

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherlamza.common.state.State
import com.example.weatherlamza.data.models.Forecast
import com.example.weatherlamza.data.models.Location
import com.example.weatherlamza.data.repositories.WeatherRepository
import com.example.weatherlamza.utils.extensions.launchWithState
import kotlinx.coroutines.flow.combine

class WeatherViewModel(private val weatherRepo: WeatherRepository) : ViewModel() {

    private var _weather = MutableLiveData<Location>()
    val weather: LiveData<Location> = _weather

    private var _dailyForecast = MutableLiveData<Forecast>()
    val dailyForecast: LiveData<Forecast> = _dailyForecast

    private var _weatherState = MutableLiveData<State>()
    val weatherState: LiveData<State> = _weatherState

    init {
        launchWithState(_weatherState) {
            weatherRepo.getWeather("Zagreb").collect { location ->
                _weather.value = location
            }
        }
    }

    fun getWeather(location: android.location.Location) {
        launchWithState(_weatherState) {
            weatherRepo.getWeatherForCoordinates(location.latitude, location.longitude)
                .combine(
                    weatherRepo.getForecast(location.latitude, location.longitude)
                ) { location, forecast ->
                    return@combine location to forecast
                }.collect {
                    _weather.value = it.first!!
                    _dailyForecast.value = it.second!!
                }
        }
    }
}

