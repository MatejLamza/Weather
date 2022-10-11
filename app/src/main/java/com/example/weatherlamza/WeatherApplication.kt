package com.example.weatherlamza

import android.app.Application
import com.example.weatherlamza.di.WeatherDI
import com.example.weatherlamza.utils.FlipperInitializer
import com.facebook.soloader.SoLoader
import dev.shreyaspatil.permissionFlow.PermissionFlow
import org.koin.android.ext.android.get

class WeatherApplication : Application() {

    companion object {
        private const val TAG = "WeatherApplication"
    }

    private val dogmaDI: WeatherDI by lazy { WeatherDI(this) }

    override fun onCreate() {
        super.onCreate()
        dogmaDI.initialize()
        PermissionFlow.init(this)

        SoLoader.init(this, false)
        initFlipper()
    }

    private fun initFlipper() {
        get<FlipperInitializer>().initialize()

    }
}
