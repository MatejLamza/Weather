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
import com.example.weatherlamza.common.state.ConnectivityState
import com.example.weatherlamza.data.local.SessionPrefs
import com.example.weatherlamza.utils.extensions.errorSnackBar
import com.example.weatherlamza.utils.extensions.infoSnackBar
import com.example.weatherlamza.utils.services.InternetConnectivityService
import com.example.weatherlamza.utils.workers.WeatherUpdateWorker
import org.koin.java.KoinJavaComponent.inject
import java.util.concurrent.TimeUnit


class MainActivity : AppCompatActivity() {

    companion object {
        const val NOTIFICATION_CHANNEL_ID = "weather_report"
        const val WORKER_DATA_ID = "worker_tag"
    }

    private val sessionPrefs by inject<SessionPrefs>(SessionPrefs::class.java)
    private val connectivityService by lazy { InternetConnectivityService(this) }

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
        connectivityService.networkStatus.asLiveData().observe(this) {
            when (it) {
                ConnectivityState.Available -> onNetworkAvailable()
                ConnectivityState.Unavailable -> onNetworkLost()
            }
        }
    }

    private fun onNetworkLost() {
        runCatching {
            errorSnackBar(findViewById(R.id.container), getString(R.string.connection_lost)).show()
        }
    }

    private fun onNetworkAvailable() {
        kotlin.runCatching {
            infoSnackBar(
                findViewById(R.id.container),
                getString(R.string.connection_available)
            ).show()
        }
    }
}
