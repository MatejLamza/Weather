package com.example.weatherlamza.navigation

import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import com.example.weatherlamza.ui.weather.WeatherFragment
import com.example.weatherlamza.ui.weather.WeatherFragmentDirections
import com.example.weatherlamza.utils.extensions.launch

class NavigationViewModel : ViewModel() {

    fun navigateToSettings(source: WeatherFragment) {
        launch {
            source.findNavController()
                .navigate(WeatherFragmentDirections.actionWeatherFragmentToSettingsFragment())
        }
    }
}
