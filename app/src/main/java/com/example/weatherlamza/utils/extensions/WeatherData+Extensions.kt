package com.example.weatherlamza.utils.extensions

import com.example.weatherlamza.data.models.WeatherData


val WeatherData.currentDateString
    get() = dtText.trim().split(" ").first().trim()
