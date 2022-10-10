package com.example.weatherlamza.utils.extensions.mappers

import com.example.weatherlamza.data.local.entity.LocationForecastEntity
import com.example.weatherlamza.data.models.Location
import java.util.*


fun Location.toEntity() =
    LocationForecastEntity(
        id = UUID.randomUUID().mostSignificantBits.toString(),
        temperature = temperature,
        weather = weather
    )
