package com.example.weatherlamza

import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.asLiveData
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.weatherlamza.common.state.ConnectivityState
import com.example.weatherlamza.data.local.SessionPrefs
import com.example.weatherlamza.databinding.ActivityMainBinding
import com.example.weatherlamza.utils.extensions.errorSnackBar
import com.example.weatherlamza.utils.extensions.getNavController
import com.example.weatherlamza.utils.extensions.infoSnackBar
import com.example.weatherlamza.utils.services.InternetConnectivityService
import com.example.weatherlamza.utils.workers.WeatherUpdateWorker
import kotlinx.coroutines.flow.distinctUntilChanged
import org.koin.java.KoinJavaComponent.inject
import java.util.concurrent.TimeUnit


class MainActivity : AppCompatActivity() {

    companion object {
        const val NOTIFICATION_CHANNEL_ID = "weather_report"
        const val WORKER_DATA_TAG = "worker_tag"
    }

    private val sessionPrefs by inject<SessionPrefs>(SessionPrefs::class.java)
    private val connectivityService by lazy { InternetConnectivityService(this) }
    private lateinit var binding: ActivityMainBinding
    private val navController: NavController by lazy { getNavController(R.id.mainContainer) }


    private val refreshWeatherInfoRequest =
        PeriodicWorkRequestBuilder<WeatherUpdateWorker>(15, TimeUnit.MINUTES).build()

    private val workManager = WorkManager.getInstance(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                NOTIFICATION_CHANNEL_ID,
                "Weather Report",
                NotificationManager.IMPORTANCE_HIGH
            )
            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }

        setUI()
        setObservers()
    }

    private fun setUI() {
        binding.navigationBar.setOnItemSelectedListener {
            val currentItemId = navController.currentDestination?.id
            if (currentItemId != it.itemId) {
                navController.navigate(
                    it.itemId, null,
                    NavOptions.Builder().setPopUpTo(it.itemId, true)
                        .build()
                )
            }
            return@setOnItemSelectedListener true
        }
    }


    private fun setObservers() {
        sessionPrefs.observePermissionForNotifications().distinctUntilChanged().asLiveData()
            .observe(this) { isPermitted ->
                if (!isPermitted) workManager.cancelUniqueWork(WORKER_DATA_TAG)
                else startUniquePeriodicWork()
            }
        connectivityService.networkStatus.distinctUntilChanged().asLiveData().observe(this) {
            when (it) {
                ConnectivityState.Available -> onNetworkAvailable()
                ConnectivityState.Unavailable -> onNetworkLost()
            }
        }
    }

    private fun startUniquePeriodicWork() {
        workManager.enqueueUniquePeriodicWork(
            WORKER_DATA_TAG,
            ExistingPeriodicWorkPolicy.KEEP,
            refreshWeatherInfoRequest
        )
    }

    private fun onNetworkLost() {
        runCatching {
            errorSnackBar(binding.root, getString(R.string.connection_lost)).show()
        }
    }

    private fun onNetworkAvailable() {
        runCatching {
            infoSnackBar(binding.root, getString(R.string.connection_available)).show()
        }
    }
}
