package com.example.weatherlamza.ui.weather

import android.os.Bundle
import android.view.View
import com.example.weatherlamza.common.base.BaseFragment
import com.example.weatherlamza.databinding.FragmentWeatherBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class WeatherFragment : BaseFragment<FragmentWeatherBinding>(FragmentWeatherBinding::inflate) {

    private val weatherViewModel by viewModel<WeatherViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupObservers()
    }

    private fun setupObservers() {
        with(weatherViewModel) {
            weather.observe(viewLifecycleOwner) { location ->
                binding.currentTemperature.text = location.temperature.temperature.toString()
            }
        }
    }
}
