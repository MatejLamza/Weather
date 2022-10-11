package com.example.weatherlamza.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.weatherlamza.data.models.Temperature
import com.example.weatherlamza.data.models.Weather
import com.example.weatherlamza.data.models.Wind


@Entity(tableName = "location_forecast")
data class LocationForecastEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Long = 0L,
    val name: String,
    val temperature: Temperature,
    val weather: List<Weather>,
    val wind: Wind
)
