package com.example.weatherlamza

import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.weatherlamza.utils.workers.WeatherUpdateWorker
import java.time.Duration

class MainActivity : AppCompatActivity() {

    private val constraints = Constraints
        .Builder()
        .setRequiredNetworkType(NetworkType.CONNECTED)
        .build()

    @RequiresApi(Build.VERSION_CODES.O)
    val refreshWeatherInfoRequest = PeriodicWorkRequestBuilder<WeatherUpdateWorker>(
        Duration.ofSeconds(10)
    ).build()

    val workManager = WorkManager.getInstance(this)

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        workManager.enqueue(refreshWeatherInfoRequest)
    }


}
