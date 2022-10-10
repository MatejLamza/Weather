package com.example.weatherlamza.di.modules

import androidx.room.Room
import com.example.weatherlamza.data.local.WeatherDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val databaseModule = module {
    single {
        Room.databaseBuilder(androidContext(), WeatherDatabase::class.java, "weather-db")
            .fallbackToDestructiveMigration()
            .build()
    }

    single { get<WeatherDatabase>().weatherForecastDAO() }
}
