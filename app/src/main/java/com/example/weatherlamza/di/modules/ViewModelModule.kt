package com.example.weatherlamza.di.modules

import com.example.weatherlamza.navigation.NavigationViewModel
import com.example.weatherlamza.ui.search.SearchViewModel
import com.example.weatherlamza.ui.weather.WeatherViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    viewModel { WeatherViewModel(weatherRepo = get()) }
    viewModel { SearchViewModel() }
    viewModel { NavigationViewModel() }
}
