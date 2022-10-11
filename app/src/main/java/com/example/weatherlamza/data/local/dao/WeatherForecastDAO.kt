package com.example.weatherlamza.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.weatherlamza.data.local.entity.LocationForecastEntity

@Dao
interface WeatherForecastDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertWeatherForecast(weatherForecastEntity: LocationForecastEntity)


    @Query("SELECT * FROM location_forecast")
    fun getWeatherForecast(): LocationForecastEntity
}
