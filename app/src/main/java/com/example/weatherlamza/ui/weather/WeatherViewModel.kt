package com.example.weatherlamza.ui.weather

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.jetweatherapp.data.model.Location
import com.example.weatherlamza.data.repositories.WeatherRepository
import com.example.weatherlamza.utils.extensions.launch

class WeatherViewModel(private val weatherRepo: WeatherRepository) : ViewModel() {

    private var _weather = MutableLiveData<Location>()
    val weather: LiveData<Location> = _weather

    init {
        launch {
            weatherRepo.getWeather("Zagreb").collect { location ->
                _weather.value = location
            }
        }
    }

}
