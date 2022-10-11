package com.example.weatherlamza.utils.extensions

import android.content.Context
import com.example.weatherlamza.R
import com.example.weatherlamza.data.models.Location
import com.example.weatherlamza.databinding.FragmentWeatherBinding


fun FragmentWeatherBinding.populateWithLocationData(currentLocation: Location, context: Context) {
    toolbarTitle.text = currentLocation.name
    currentTemperature.text = currentLocation.temperature.temperature.toInt().toString()
    description.text = currentLocation.weather.first().description.uppercase()

    temperatureHigh.text = context.getString(
        R.string.temperature_high,
        currentLocation.temperature.tempMax.toInt().toString()
    )
    temperatureLow.text = context.getString(
        R.string.temperature_low,
        currentLocation.temperature.tempMin.toInt().toString()
    )

    humidity.text = context.getString(
        R.string.humidity, currentLocation.temperature.humidity.toInt().toString()
    )
    pressure.text = context.getString(
        R.string.pressure,
        currentLocation.temperature.pressure.toInt().toString()
    )
    wind.text = context.getString(R.string.wind, currentLocation.wind.speed.toInt().toString())
}
