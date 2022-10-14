package com.example.weatherlamza.ui.weather

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.weatherlamza.common.base.BaseFragment
import com.example.weatherlamza.databinding.FragmentWeatherV2Binding
import com.example.weatherlamza.ui.weather.adapters.DailyWeatherForecastAdapter
import com.example.weatherlamza.ui.weather.adapters.HourlyWeatherForecastAdapter
import com.example.weatherlamza.utils.extensions.*
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.math.abs

class WeatherFragment : BaseFragment<FragmentWeatherV2Binding>(FragmentWeatherV2Binding::inflate) {

    private val weatherViewModel by viewModel<WeatherViewModel>()
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val dailyForecastAdapter by lazy { DailyWeatherForecastAdapter() }
    private val hourlyForecastAdapter by lazy { HourlyWeatherForecastAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUI()
        setupObservers()
        setupListeners()
        requestLastLocation()
    }

    private fun setUI() {
        with(binding) {
            dailyForecast.adapter = dailyForecastAdapter
            hourlyForecastList.adapter = hourlyForecastAdapter
        }
    }

    private fun setupObservers() {
        with(weatherViewModel) {
            weather.observe(viewLifecycleOwner) { location ->
                binding.populateWithLocationData(location, requireContext())
            }
            dailyForecast.observe(viewLifecycleOwner) { forecast ->
                binding.updateForecastUI(forecast)
                dailyForecastAdapter.dailyWeatherForecast = forecast.weatherData
            }
            hourlyForecast.observe(viewLifecycleOwner) {
                hourlyForecastAdapter.hourlyWeatherForecast = it
            }
            weatherState.observeState(viewLifecycleOwner, this@WeatherFragment) {}
        }
    }

    private fun setupListeners() {
        with(binding) {
            /* weatherContent.setOnRefreshListener {
                 requestLastLocation()
                 binding.weatherContent.isRefreshing = false
             }*/

            topBarLayout.addOnOffsetChangedListener { appBarLayout, verticalOffset ->
                val isCollapsed = abs(verticalOffset) - appBarLayout!!.totalScrollRange == 0

                Log.d(TAG, "vertical offset $verticalOffset: ")

                if (isCollapsed) {
                    Log.d("bbb", "tu sam is collapsed: ")
                    toolbar.visible()
                } else {
                    Log.d("bbb", "tu sam is displayan je: ")

                    toolbar.gone()
                }
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun requestLastLocation() {
        checkPermissions(permissions,
            onSuccess = {
                fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                    weatherViewModel.getWeather(
                        location
                    )
                }
            },
            onError = {
                Toast.makeText(
                    requireContext(),
                    "Please enable permissions to get forecast for current location",
                    Toast.LENGTH_LONG
                ).show()
            })
    }
}
