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

    private val weatherRepo by inject<WeatherRepository>(WeatherRepository::class.java)

    private val notificationService = WeatherReportNotificationService(context)

    override suspend fun doWork(): Result {
        return withContext(Dispatchers.IO) {
            return@withContext kotlin.runCatching {
                Log.d("bbb", "doWork called pinging api")
                weatherRepo.getWeather("Zagreb").collect {
                    Log.d("bbb", "API CALL RESULT: ${it.temperature.temperature}")
                }
                notificationService.showNotification()
                Result.success()
            }.getOrDefault(Result.failure())
        }
    }
}
