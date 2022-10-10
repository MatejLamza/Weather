package com.example.weatherlamza.ui.weather

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherlamza.common.state.State
import com.example.weatherlamza.data.models.Location
import com.example.weatherlamza.data.models.WeatherData
import com.example.weatherlamza.data.repositories.WeatherRepository
import com.example.weatherlamza.utils.extensions.launch
import com.example.weatherlamza.utils.extensions.launchWithState

class WeatherViewModel(private val weatherRepo: WeatherRepository) : ViewModel() {

    private var _weather = MutableLiveData<Location>()
    val weather: LiveData<Location> = _weather

    private var _dailyForecast = MutableLiveData<List<WeatherData>>()
    val dailyForecast: LiveData<List<WeatherData>> = _dailyForecast

    private var _weatherState = MutableLiveData<State>()
    val weatherState: LiveData<State> = _weatherState

    init {
        launchWithState(_weatherState) {
            weatherRepo.getWeather("Zagreb").collect { location ->
                _weather.value = location
            }
        }
    }

    fun getWeatherForCurrentLocation(location: android.location.Location) {
        launch {
            weatherRepo.getWeatherForCoordinates(location.latitude, location.longitude).collect {
                _weather.value = it
            }
        }
    }

    fun getForecastForCurrentLocation(location: android.location.Location) {
        launch {
            weatherRepo.getForecast(location.latitude, location.longitude).collect {
                _dailyForecast.value = it.weatherData
            }
        }
    }

    fun getWeatherForQuery(query: String) {
        launch {
            weatherRepo.getWeather(query).collect {
                _weather.value = it
            }
        }
    }
}

