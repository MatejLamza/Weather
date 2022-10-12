package com.example.weatherlamza.di.modules

import android.content.Context
import com.example.weatherlamza.BuildConfig
import com.example.weatherlamza.data.local.SessionPrefs
import com.example.weatherlamza.utils.FlipperInitializer
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val appModule = module {
    single {
        androidContext().getSharedPreferences(
            BuildConfig.APPLICATION_ID,
            Context.MODE_PRIVATE
        )
    }
    single { FlipperInitializer(androidApplication()) }
    single { SessionPrefs(sharedPreferences = get()) }
}
