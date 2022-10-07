package com.example.weatherlamza.ui.weather

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.weatherlamza.common.base.BaseFragment
import com.example.weatherlamza.databinding.FragmentWeatherBinding
import com.example.weatherlamza.utils.extensions.checkPermissions
import com.example.weatherlamza.utils.extensions.observeState
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import org.koin.androidx.viewmodel.ext.android.viewModel

class WeatherFragment : BaseFragment<FragmentWeatherBinding>(FragmentWeatherBinding::inflate) {

    private val weatherViewModel by viewModel<WeatherViewModel>()
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupObservers()
        setupListeners()
    }

    private fun setupObservers() {
        with(weatherViewModel) {
            weather.observe(viewLifecycleOwner) { location ->
                binding.currentTemperature.text = location.temperature.temperature.toString()
            }
            weatherState.observeState(viewLifecycleOwner, this@WeatherFragment) {}
        }
    }

    override fun onResume() {
        super.onResume()
        requestLastLocation()
    }

    private fun setupListeners() {
        binding.weatherContent.setOnRefreshListener {
            requestLastLocation()
            binding.weatherContent.isRefreshing = false
        }
    }

    @SuppressLint("MissingPermission")
    private fun requestLastLocation() {
        checkPermissions(permissions,
            onSuccess = {
                fusedLocationClient.lastLocation.addOnSuccessListener { location ->

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

}
