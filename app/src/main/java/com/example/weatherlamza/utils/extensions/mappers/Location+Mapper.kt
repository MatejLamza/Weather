package com.example.weatherlamza.utils.extensions.mappers

import com.example.weatherlamza.data.local.entity.LocationForecastEntity
import com.example.weatherlamza.data.models.Location
import java.util.*


fun Location.toEntity() =
    LocationForecastEntity(
        id = UUID.randomUUID().mostSignificantBits.toString(),
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
