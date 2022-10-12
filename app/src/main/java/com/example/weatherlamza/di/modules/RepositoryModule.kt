package com.example.weatherlamza.di.modules

import com.example.weatherlamza.data.local.SessionRepository
import com.example.weatherlamza.data.local.SessionRepositoryImpl
import com.example.weatherlamza.data.repositories.SearchRepository
import com.example.weatherlamza.data.repositories.WeatherRepository
import com.example.weatherlamza.data.repositories.impl.SearchRepositoryImpl
import com.example.weatherlamza.data.repositories.impl.WeatherRepositoryImpl
import org.koin.dsl.module


val repositoryModule = module {

    single<WeatherRepository> {
        WeatherRepositoryImpl(api = get(), weatherDB = get(), recentSearchesDAO = get())
    }

    single<SessionRepository> { SessionRepositoryImpl(sessionPrefs = get()) }
    single<SearchRepository> { SearchRepositoryImpl(searchDAO = get()) }
}
