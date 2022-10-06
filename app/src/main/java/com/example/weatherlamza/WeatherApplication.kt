package com.example.weatherlamza

import android.app.Application
import com.example.weatherlamza.di.WeatherDI

class WeatherApplication : Application() {

    companion object {
        private const val TAG = "WeatherApplication"
    }

    private val dogmaDI: WeatherDI by lazy { WeatherDI(this) }

    override fun onCreate() {
        super.onCreate()
        dogmaDI.initialize()
    }
}
