package com.example.weatherlamza.di.modules

import com.example.weatherlamza.BuildConfig
import com.example.weatherlamza.data.network.WeatherAPI
import com.example.weatherlamza.utils.interceptors.AuthInterceptor
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


val networkModule = module {

    single {
        GsonBuilder()
            .setLenient()
            .create()
    }

    single<GsonConverterFactory> {
        GsonConverterFactory.create(get<Gson>())
    }

    single { AuthInterceptor() }

    single {
        OkHttpClient()
            .newBuilder()
            .addInterceptor(get<AuthInterceptor>())
            .build()
    }

    single {
        Retrofit
            .Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(get<GsonConverterFactory>())
            .client(get())
            .build()
    }

    single { get<Retrofit>().create(WeatherAPI::class.java) }

}
