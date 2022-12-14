package com.example.weatherlamza.di.modules

import com.example.weatherlamza.MainViewModel
import com.example.weatherlamza.navigation.NavigationViewModel
import com.example.weatherlamza.ui.search.SearchViewModel
import com.example.weatherlamza.ui.settings.SettingsViewModel
import com.example.weatherlamza.ui.weather.WeatherViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    viewModel { WeatherViewModel(weatherRepo = get()) }
    viewModel { SearchViewModel(weatherRepo = get(), searchRepo = get()) }
    viewModel { SettingsViewModel(sessionRepository = get()) }
    viewModel { NavigationViewModel() }
    viewModel { MainViewModel(sessionPrefs = get()) }
}
