package com.example.weatherlamza.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.weatherlamza.data.models.City
import com.example.weatherlamza.data.models.WeatherData

@Entity(tableName = "forecast")
data class ForecastEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Long = 0L,
    val city: City,
    val weatherData: List<WeatherData>,
)
