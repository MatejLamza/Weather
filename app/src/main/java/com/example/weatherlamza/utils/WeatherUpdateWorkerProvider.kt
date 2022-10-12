package com.example.weatherlamza.utils

import android.content.Context
import android.location.Location
import androidx.work.*
import com.example.weatherlamza.MainActivity
import com.example.weatherlamza.utils.workers.WeatherUpdateWorker
import java.util.concurrent.TimeUnit

class WeatherUpdateWorkerProvider(private val context: Context) {

    private val constraints = Constraints.Builder()
        .setRequiredNetworkType(NetworkType.CONNECTED)
        .build()

    private val refreshWeatherInfoRequest =
        PeriodicWorkRequestBuilder<WeatherUpdateWorker>(2, TimeUnit.MINUTES)
            .setConstraints(constraints)

    private val workManager by lazy { WorkManager.getInstance(context) }

    fun startUniquePeriodicWork(currentLocation: Location) {
        val locationArray = arrayOf(currentLocation.latitude, currentLocation.longitude)

        val data = Data.Builder()
            .putDoubleArray(MainActivity.WORKER_DATA_KEY, locationArray.toDoubleArray())
            .build()

        workManager.enqueueUniquePeriodicWork(
            MainActivity.WORKER_DATA_TAG,
            ExistingPeriodicWorkPolicy.KEEP,
            refreshWeatherInfoRequest.setInputData(data).build()
        )
    }

    fun cancelUniquePeriodicWork() {
        workManager.cancelUniqueWork(MainActivity.WORKER_DATA_TAG)
    }

}
