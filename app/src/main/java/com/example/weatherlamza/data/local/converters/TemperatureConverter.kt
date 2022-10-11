package com.example.weatherlamza.data.local.converters

import androidx.room.TypeConverter
import com.example.weatherlamza.data.models.Temperature
import com.example.weatherlamza.data.models.Weather
import com.example.weatherlamza.data.models.Wind
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
    fun fromWind(value: Wind?): String? {
        return value?.let { gson.toJson(it) }
    }

    @TypeConverter
    fun toWind(windString: String?): Wind? {
        return windString?.let { gson.fromJson(it, Wind::class.java) }
    }

    @TypeConverter
    fun weatherListToJson(value: List<Weather>?): String? {
        return gson.toJson(value)
    }

    @TypeConverter
    fun weatherListFromJson(json: String?): List<Weather>? {
        return if (json.isNullOrEmpty()) null
        else gson.fromJson<List<Weather>>(json, Weather::class.java)
    }

}
