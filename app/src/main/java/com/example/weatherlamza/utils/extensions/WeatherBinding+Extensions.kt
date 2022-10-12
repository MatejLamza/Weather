package com.example.weatherlamza.utils.extensions

import android.content.Context
import androidx.core.content.ContextCompat
import com.example.weatherlamza.R
import com.example.weatherlamza.data.models.City
import com.example.weatherlamza.data.models.Forecast
import com.example.weatherlamza.data.models.Location
import com.example.weatherlamza.databinding.FragmentWeatherBinding
import org.joda.time.Instant
import org.joda.time.LocalDateTime
import org.joda.time.format.DateTimeFormat


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

    weatherDetails.humidity.text = context.getString(
        R.string.humidity, currentLocation.temperature.humidity.toInt().toString()
    )
    weatherDetails.pressure.text = context.getString(
        R.string.pressure,
        currentLocation.temperature.pressure.toInt().toString()
    )
    weatherDetails.wind.text =
        context.getString(R.string.wind, currentLocation.wind.speed.toInt().toString())
}

fun FragmentWeatherBinding.updateForecastUI(forecast: Forecast) {
    changeBackgroundDependingOnTheTimeOfDay(forecast)
    setSunriseSunset(forecast.city)
}

fun FragmentWeatherBinding.setSunriseSunset(city: City) {
    val sunset = Instant.ofEpochSecond(city.sunset.toLong()).toDateTime().toLocalTime()
    val sunrise = Instant.ofEpochSecond(city.sunrise.toLong()).toDateTime().toLocalTime()

    val timeFormatter = DateTimeFormat.forPattern("HH:mm")
    val sunriseStr = timeFormatter.print(sunrise)
    val sunsetStr = timeFormatter.print(sunset)

    sunsetSunriseContainer.sunrise.text = sunriseStr.toString()
    sunsetSunriseContainer.sunset.text = sunsetStr.toString()
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

