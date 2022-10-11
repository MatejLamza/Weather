package com.example.weatherlamza.ui.weather

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.widget.Toast
import com.example.weatherlamza.R
import com.example.weatherlamza.common.base.BaseFragment
import com.example.weatherlamza.databinding.FragmentWeatherBinding
import com.example.weatherlamza.ui.weather.adapters.DailyWeatherForecastAdapter
import com.example.weatherlamza.utils.extensions.checkPermissions
import com.example.weatherlamza.utils.extensions.observeState
import com.example.weatherlamza.utils.extensions.populateWithLocationData
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import org.koin.androidx.viewmodel.ext.android.viewModel

class WeatherFragment : BaseFragment<FragmentWeatherBinding>(FragmentWeatherBinding::inflate) {

    private val weatherViewModel by viewModel<WeatherViewModel>()
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val dailyForecastAdapter by lazy { DailyWeatherForecastAdapter() }

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
            dailyForecast.adapter = dailyForecastAdapter
            setupToolbar(binding.myToolbar)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        R.menu.main_menu.let { inflater.inflate(it, menu) }
        super.onCreateOptionsMenu(menu, inflater)
    }

    private fun setupObservers() {
        with(weatherViewModel) {
            weather.observe(viewLifecycleOwner) { location ->
                binding.populateWithLocationData(location, requireContext())
            }
            dailyForecast.observe(viewLifecycleOwner) { forecast ->
                Log.d("bbb", "setupObservers: tu sam forecast")
                dailyForecastAdapter.dailyWeatherForecast = forecast
            }
            weatherState.observeState(viewLifecycleOwner, this@WeatherFragment) {}
            dailyForecast.observe(viewLifecycleOwner) {
                dailyForecastAdapter.dailyWeatherForecast = it
            }
        }
    }

    private fun setupListeners() {
        with(binding) {
            weatherContent.setOnRefreshListener {
                requestLastLocation()
                binding.weatherContent.isRefreshing = false
            }

            myToolbar.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.search_menu_item -> {
                        navigation.navigateToSearch(this@WeatherFragment)
                        true
                    }
                    R.id.settings_menu_item -> {
                        navigation.navigateToSettings(this@WeatherFragment)
                        true
                    }
                    else -> false
                }
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun requestLastLocation() {
        checkPermissions(permissions,
            onSuccess = {
                fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                    weatherViewModel.getWeather(location)
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

    override fun onResume() {
        super.onResume()
        requestLastLocation()
    }
}
