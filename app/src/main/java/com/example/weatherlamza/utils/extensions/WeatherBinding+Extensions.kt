package com.example.weatherlamza.utils.extensions

import android.content.Context
import com.example.weatherlamza.R
import com.example.weatherlamza.data.models.City
import com.example.weatherlamza.data.models.Forecast
import com.example.weatherlamza.data.models.Location
import com.example.weatherlamza.databinding.FragmentWeatherV2Binding
import org.joda.time.Instant
import org.joda.time.LocalDateTime
import org.joda.time.format.DateTimeFormat


fun FragmentWeatherV2Binding.populateWithLocationData(currentLocation: Location, context: Context) {
   /* weatherInfo.location.text = currentLocation.name
    weatherInfo.temperature.text = currentLocation.temperature.temperature.toInt().toString()
    weatherInfo.feelsLike.text =
        "${currentLocation.temperature.tempMax}° / ${currentLocation.temperature.tempMin}° Feels like 12°"*/

    weatherDetails.humidity.text = context.getString(
        R.string.humidity, currentLocation.temperature.humidity.toInt().toString()
    )
    weatherDetails.uvIndex.text = "Low"
    weatherDetails.wind.text =
        context.getString(R.string.wind, currentLocation.wind.speed.toInt().toString())
}

fun FragmentWeatherV2Binding.updateForecastUI(forecast: Forecast) {
//    changeBackgroundDependingOnTheTimeOfDay(forecast)
    setSunriseSunset(forecast.city)
}

fun FragmentWeatherV2Binding.setSunriseSunset(city: City) {
    val sunset = Instant.ofEpochSecond(city.sunset.toLong()).toDateTime().toLocalTime()
    val sunrise = Instant.ofEpochSecond(city.sunrise.toLong()).toDateTime().toLocalTime()

    val timeFormatter = DateTimeFormat.forPattern("HH:mm")
    val sunriseStr = timeFormatter.print(sunrise)
    val sunsetStr = timeFormatter.print(sunset)

    sunsetSunriseContainer.sunrise.text = sunriseStr.toString()
    sunsetSunriseContainer.sunset.text = sunsetStr.toString()
}

/*
fun FragmentWeatherV2Binding.changeBackgroundDependingOnTheTimeOfDay(forecast: Forecast) {
    weatherContent.background =
        ContextCompat.getDrawable(root.context, getCorrectBackgroundForTimeOfDay(forecast))
}
*/

private fun getCorrectBackgroundForTimeOfDay(forecast: Forecast): Int {
    val currentTime = LocalDateTime.now().toLocalTime()
    val sunsetDateTime =
        Instant.ofEpochSecond(forecast.city.sunset.toLong())
            .toDateTime()
            .toLocalTime()

    return if (currentTime < sunsetDateTime) R.drawable.background_day else R.drawable.background_night
}

