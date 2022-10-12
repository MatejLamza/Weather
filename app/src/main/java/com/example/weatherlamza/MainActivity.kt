package com.example.weatherlamza

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.asLiveData
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.work.Data
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.weatherlamza.common.base.BaseFragment
import com.example.weatherlamza.common.state.ConnectivityState
import com.example.weatherlamza.databinding.ActivityMainBinding
import com.example.weatherlamza.utils.extensions.*
import com.example.weatherlamza.utils.services.InternetConnectivityService
import com.example.weatherlamza.utils.workers.WeatherUpdateWorker
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.flow.distinctUntilChanged
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.concurrent.TimeUnit


class MainActivity : AppCompatActivity() {

    companion object {
        const val NOTIFICATION_CHANNEL_ID = "weather_report"
        const val WORKER_DATA_TAG = "worker_tag"
    }

    private val connectivityService by lazy { InternetConnectivityService(this) }
    private val navController: NavController by lazy { getNavController(R.id.mainContainer) }
    private val mainViewModel by viewModel<MainViewModel>()

    private lateinit var binding: ActivityMainBinding
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private val workManager = WorkManager.getInstance(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
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
        requestLastLocation()
        setUI()
        setObservers()
    }

    @SuppressLint("MissingPermission")
    private fun requestLastLocation() {
        checkPermissions(
            BaseFragment.permissions,
            onSuccess = {
                fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                    mainViewModel.currentLocation.value = location
                }
            },
            onError = {
                Toast.makeText(
                    this,
                    "Permissions not granted",
                    Toast.LENGTH_SHORT
                ).show()
            })
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
        connectivityService.networkStatus.distinctUntilChanged().asLiveData().observe(this) {
            when (it) {
                ConnectivityState.Available -> onNetworkAvailable()
                ConnectivityState.Unavailable -> onNetworkLost()
            }
        }
        mainViewModel.locationWithPermission.observe(this) {
            if (!it.areNotificationsEnabled) workManager.cancelUniqueWork(WORKER_DATA_TAG)
            else startUniquePeriodicWork(it.currentLocation)
        }
    }

    private fun startUniquePeriodicWork(currentLocation: Location) {
        val locationArray = arrayOf(currentLocation.latitude, currentLocation.longitude)

        val data = Data.Builder().putDoubleArray("location", locationArray.toDoubleArray())
            .build()

        val refreshWeatherInfoRequest =
            PeriodicWorkRequestBuilder<WeatherUpdateWorker>(2, TimeUnit.MINUTES).setInputData(data)
                .build()

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
