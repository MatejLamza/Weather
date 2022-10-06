package com.example.weatherlamza.di.modules

import com.example.weatherlamza.BuildConfig
import com.example.weatherlamza.di.Qualifiers
import com.example.weatherlamza.utils.interceptors.AuthInterceptor
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


val networkModule = module {

    single(Qualifiers.gson) {
        GsonBuilder()
            .setLenient()
            .create()
    }

    single<GsonConverterFactory> {
        GsonConverterFactory.create(get(Qualifiers.gson))
    }

    single(Qualifiers.authInterceptor) { AuthInterceptor() }

    single(Qualifiers.okHttpClient) {
        OkHttpClient()
            .newBuilder()
            .addInterceptor(get(Qualifiers.authInterceptor))
            .build()
    }

    single {
        Retrofit
            .Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(get<GsonConverterFactory>())
            .client(get(Qualifiers.okHttpClient))
            .build()
    }


}
