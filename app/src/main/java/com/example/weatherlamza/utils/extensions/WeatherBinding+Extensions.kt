package com.example.weatherlamza.utils.extensions

import android.content.Context
import androidx.core.content.ContextCompat
import com.example.weatherlamza.R
import com.example.weatherlamza.data.models.Forecast
import com.example.weatherlamza.data.models.Location
import com.example.weatherlamza.databinding.FragmentWeatherBinding
import org.joda.time.Instant
import org.joda.time.LocalDateTime


fun FragmentWeatherBinding.populateWithLocationData(currentLocation: Location, context: Context) {
    location.text = currentLocation.name
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

fun FragmentWeatherBinding.changeBackgroundDependingOnTheTimeOfDay(forecast: Forecast) {
    weatherContent.background =
        ContextCompat.getDrawable(root.context, getCorrectBackgroundForTimeOfDay(forecast))
}

private fun getCorrectBackgroundForTimeOfDay(forecast: Forecast): Int {
    val currentTime = LocalDateTime.now().toLocalTime()
    val sunsetDateTime =
        Instant.ofEpochSecond(forecast.city.sunset.toLong())
            .toDateTime()
            .toLocalTime()

    return if (currentTime < sunsetDateTime) R.drawable.background_day else R.drawable.background_night
}

