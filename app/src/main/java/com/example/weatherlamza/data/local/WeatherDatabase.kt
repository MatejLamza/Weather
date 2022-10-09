package com.example.weatherlamza.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.weatherlamza.data.local.dao.WeatherForecastDAO
import com.example.weatherlamza.data.local.entity.LocationForecastEntity


@Database(
    entities = [LocationForecastEntity::class],
    version = 1,
    exportSchema = false
)
abstract class WeatherDatabase : RoomDatabase() {

    abstract fun weatherForecastDAO(): WeatherForecastDAO
}
