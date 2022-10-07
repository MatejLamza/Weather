package com.example.weatherlamza.ui.weather

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.jetweatherapp.data.model.Location
import com.example.weatherlamza.common.state.State
import com.example.weatherlamza.data.repositories.WeatherRepository
import com.example.weatherlamza.utils.extensions.launchWithState

class WeatherViewModel(private val weatherRepo: WeatherRepository) : ViewModel() {

    private var _weather = MutableLiveData<Location>()
    val weather: LiveData<Location> = _weather

    private var _weatherState = MutableLiveData<State>()
    val weatherState: LiveData<State> = _weatherState

    init {
        launchWithState(_weatherState) {
            weatherRepo.getWeather("Zagreb").collect { location ->
                _weather.value = location
            }
        }
    }

}
