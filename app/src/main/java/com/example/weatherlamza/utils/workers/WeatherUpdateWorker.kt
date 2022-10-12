package com.example.weatherlamza.utils.workers

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.weatherlamza.data.repositories.WeatherRepository
import com.example.weatherlamza.utils.services.WeatherReportNotificationService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.java.KoinJavaComponent.inject

class WeatherUpdateWorker(private val context: Context, workerParams: WorkerParameters) :
    CoroutineWorker(context, workerParams) {

    /**
     * this list is taken from https://openweathermap.org/weather-conditions
     * When worker pings api it will check if description is inside of this list
     * and if it is it will send notification to user.
     */
    private val listOfWeathersToNotifyUser = listOf(
        "shower rain",
        "rain",
        "thunderstorm",
        "snow",
        "light thunderstorm",
        "heavy thunderstorm",
        "drizzle",
        "moderate rain",
        "light rain",
        "heavy snow",
        "light snow"
    )

    private val weatherRepo by inject<WeatherRepository>(WeatherRepository::class.java)

    private val notificationService = WeatherReportNotificationService(context)

    override suspend fun doWork(): Result {
        return withContext(Dispatchers.IO) {
            return@withContext runCatching {
                Log.d("bbb", "doWork: ")
                val location = inputData.getDoubleArray("location")
                var description = ""

                if (location != null) {
                    weatherRepo.getWeatherForCoordinates(location[0], location[1])
                        .collect { description = it.weather[0].description }
                }

                if (!listOfWeathersToNotifyUser.contains(description)) {
                    notificationService.showNotification(title = "Weather warning", description)
                    return@withContext Result.success()
                } else return@withContext Result.failure()

            }.getOrDefault(Result.failure())
        }
    }
}
