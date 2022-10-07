package com.example.weatherlamza.di.modules

import com.example.weatherlamza.data.repositories.WeatherRepository
import com.example.weatherlamza.data.repositories.impl.WeatherRepositoryImpl
import org.koin.dsl.module


val repositoryModule = module {

    single<WeatherRepository> {
        WeatherRepositoryImpl(api = get())
    }
}
