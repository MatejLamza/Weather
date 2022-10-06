package com.example.weatherlamza.di

import org.koin.core.qualifier.named

object Qualifiers {
    val okHttpClient by lazy { named("OkHttpClient") }
    val gson by lazy { named("Gson") }
    val authInterceptor by lazy { named("AuthInterceptor") }
}
