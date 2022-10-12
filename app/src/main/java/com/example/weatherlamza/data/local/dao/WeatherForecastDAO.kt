package com.example.weatherlamza.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.weatherlamza.data.local.entity.ForecastEntity
import com.example.weatherlamza.data.local.entity.LocationForecastEntity

@Dao
interface WeatherForecastDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertWeatherForecast(weatherForecastEntity: LocationForecastEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDailyForecast(forecastEntity: ForecastEntity)

    @Query("SELECT * FROM location_forecast")
    fun getWeatherForecast(): LocationForecastEntity

    @Query("SELECT * FROM forecast")
    fun getDailyForecast(): ForecastEntity
}
