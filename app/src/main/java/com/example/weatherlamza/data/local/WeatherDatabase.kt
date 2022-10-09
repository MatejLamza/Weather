package com.example.weatherlamza.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.weatherlamza.data.local.converters.TemperatureConverter
import com.example.weatherlamza.data.local.dao.WeatherForecastDAO
import com.example.weatherlamza.data.local.entity.LocationForecastEntity


@Database(
    entities = [LocationForecastEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(TemperatureConverter::class)
abstract class WeatherDatabase : RoomDatabase() {

    abstract fun weatherForecastDAO(): WeatherForecastDAO
}
