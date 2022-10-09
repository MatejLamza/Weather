package com.example.weatherlamza.data.local.converters

import androidx.room.TypeConverter
import com.example.weatherlamza.data.models.Temperature
import com.example.weatherlamza.data.models.Weather
import com.google.gson.Gson

class TemperatureConverter {

    private val gson: Gson by lazy { Gson() }

    @TypeConverter
    fun fromTemperature(value: Temperature?): String? {
        return value?.let { gson.toJson(it) }
    }

    @TypeConverter
    fun toTemperature(temperatureString: String?): Temperature? {
        return temperatureString?.let { gson.fromJson(it, Temperature::class.java) }
    }

    @TypeConverter
    fun fromWeather(value: Weather?): String? {
        return value?.let { gson.toJson(it) }
    }

    @TypeConverter
    fun toWeather(temperatureString: String?): Weather? {
        return temperatureString?.let { gson.fromJson(it, Weather::class.java) }
    }


}
