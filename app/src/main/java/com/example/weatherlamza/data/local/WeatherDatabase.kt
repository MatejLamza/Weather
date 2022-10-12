package com.example.weatherlamza.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.weatherlamza.data.local.converters.TemperatureConverter
import com.example.weatherlamza.data.local.dao.SearchDAO
import com.example.weatherlamza.data.local.dao.WeatherForecastDAO
import com.example.weatherlamza.data.local.entity.ForecastEntity
import com.example.weatherlamza.data.local.entity.LocationForecastEntity
import com.example.weatherlamza.data.local.entity.RecentSearchesEntity


@Database(
    entities = [LocationForecastEntity::class, RecentSearchesEntity::class, ForecastEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(TemperatureConverter::class)
abstract class WeatherDatabase : RoomDatabase() {

    abstract fun weatherForecastDAO(): WeatherForecastDAO
    abstract fun searchDAO(): SearchDAO
}
