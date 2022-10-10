package com.example.weatherlamza

import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.asLiveData
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.weatherlamza.data.local.SessionPrefs
import com.example.weatherlamza.utils.workers.WeatherUpdateWorker
import org.koin.java.KoinJavaComponent.inject
import java.util.concurrent.TimeUnit


class MainActivity : AppCompatActivity() {

    companion object {
        const val NOTIFICATION_CHANNEL_ID = "weather_report"
        const val WORKER_DATA_ID = "worker_tag"
    }

    private val sessionPrefs by inject<SessionPrefs>(SessionPrefs::class.java)

    @RequiresApi(Build.VERSION_CODES.O)
    val refreshWeatherInfoRequest =
        PeriodicWorkRequestBuilder<WeatherUpdateWorker>(2, TimeUnit.MINUTES).build()


    @RequiresApi(Build.VERSION_CODES.O)
    val workManager =
        WorkManager.getInstance(this).getWorkInfoByIdLiveData(refreshWeatherInfoRequest.id)

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                NOTIFICATION_CHANNEL_ID,
                "Weather Report",
                NotificationManager.IMPORTANCE_HIGH
            )
            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
            Log.d("bbb", "onCreate: kreiram notif channel")
        }

        setObservers()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setObservers() {
        workManager.observe(this) {}
        sessionPrefs.observePermissionForNotifications().asLiveData().observe(this) { isPermitted ->
            Log.d("bbb", "Are notifications permitted: $isPermitted ")
        }
    }

}
