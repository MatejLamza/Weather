package com.example.weatherlamza.utils.extensions.mappers

import com.example.weatherlamza.data.local.entity.ForecastEntity
import com.example.weatherlamza.data.models.Forecast


fun Forecast.toEntity() =
    ForecastEntity(
        city = city,
        weatherData = weatherData
    )

fun ForecastEntity.toDomain() =
    Forecast(city, weatherData)
