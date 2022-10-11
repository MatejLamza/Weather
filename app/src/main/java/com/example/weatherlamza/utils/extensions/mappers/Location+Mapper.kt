package com.example.weatherlamza.utils.extensions.mappers

import com.example.weatherlamza.data.local.entity.LocationForecastEntity
import com.example.weatherlamza.data.models.Location


fun Location.toEntity() =
    LocationForecastEntity(
        name = name,
        temperature = temperature,
        weather = weather,
        wind = wind
    )

fun LocationForecastEntity.toDomain() =
    Location(
        name = name,
        temperature = temperature,
        weather = weather,
        wind = wind
    )
