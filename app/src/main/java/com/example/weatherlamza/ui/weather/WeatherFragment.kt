package com.example.weatherlamza.ui.weather

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.weatherlamza.R
import com.example.weatherlamza.common.base.BaseFragment
import com.example.weatherlamza.data.models.Location
import com.example.weatherlamza.databinding.FragmentWeatherBinding
import com.example.weatherlamza.ui.weather.adapters.DailyWeatherForecastAdapter
import com.example.weatherlamza.utils.extensions.checkPermissions
import com.example.weatherlamza.utils.extensions.observeState
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import org.koin.androidx.viewmodel.ext.android.viewModel

class WeatherFragment : BaseFragment<FragmentWeatherBinding>(FragmentWeatherBinding::inflate) {

    private val weatherViewModel by viewModel<WeatherViewModel>()
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val dailyForecastAdapter by lazy {
        DailyWeatherForecastAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUI()
        setupObservers()
        setupListeners()
    }

    private fun setUI() {
        with(binding) {

//            forecast.adapter = dailyForecastAdapter
        }
    }

    private fun setupObservers() {
        with(weatherViewModel) {
            weather.observe(viewLifecycleOwner) { location ->
                setLocation(location)
            }
            weatherState.observeState(viewLifecycleOwner, this@WeatherFragment) {}
            dailyForecast.observe(viewLifecycleOwner) {
                dailyForecastAdapter.dailyWeatherForecast = it
            }
        }
    }

    private fun setLocation(currentLocation: Location) {
        with(binding) {
            location.text = currentLocation.name
            currentTemperature.text = currentLocation.temperature.temperature.toInt().toString()
            description.text = currentLocation.weather.first().description.uppercase()

            temperatureHigh.text = getString(
                R.string.temperature_high,
                currentLocation.temperature.tempMax.toInt().toString()
            )
            temperatureLow.text = getString(
                R.string.temperature_low,
                currentLocation.temperature.tempMin.toInt().toString()
            )

            humidity.text = getString(
                R.string.humidity,
                currentLocation.temperature.humidity.toInt().toString()
            )
            pressure.text = getString(
                R.string.pressure,
                currentLocation.temperature.pressure.toInt().toString()
            )
            wind.text = getString(
                R.string.wind,
                currentLocation.wind.speed.toInt().toString()
            )
        }
    }

    private fun setupListeners() {
        with(binding) {
            // TODO refresh for current city not for last location
            weatherContent.setOnRefreshListener {
                requestLastLocation()
                binding.weatherContent.isRefreshing = false
            }
            currentTemperature.setOnClickListener {
                navigation.navigateToSettings(this@WeatherFragment)
            }

        }
    }

    @SuppressLint("MissingPermission")
    private fun requestLastLocation() {
        checkPermissions(permissions,
            onSuccess = {
                fusedLocationClient.lastLocation.addOnSuccessListener { location ->

                    weatherViewModel.getForecastForCurrentLocation(location)
                    weatherViewModel.getWeatherForCurrentLocation(location)
                }
            },
            onError = {
                Toast.makeText(
                    requireContext(),
                    "Permissions not granted",
                    Toast.LENGTH_SHORT
                ).show()
            })
    }

//    override fun onQueryTextSubmit(query: String?): Boolean {
//        binding.search.clearFocus()
//        query?.let { weatherViewModel.getWeatherForQuery(it) }
//        return true
//    }
//
//    override fun onQueryTextChange(newText: String?): Boolean = true

    override fun onResume() {
        super.onResume()
        requestLastLocation()
    }

}
