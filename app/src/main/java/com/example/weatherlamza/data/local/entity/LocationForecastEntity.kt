package com.example.weatherlamza.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "location_forecast")
data class LocationForecastEntity(
    @PrimaryKey
    val id: String,


    )
