package com.example.weatherlamza.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.weatherlamza.data.models.Temperature
import com.example.weatherlamza.data.models.Weather


@Entity(tableName = "location_forecast")
data class LocationForecastEntity(
    @PrimaryKey
    val id: String,
    val temperature: Temperature,
    val weather: List<Weather>
)
