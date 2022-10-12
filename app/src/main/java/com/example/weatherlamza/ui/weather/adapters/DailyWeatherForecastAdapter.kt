package com.example.weatherlamza.ui.weather.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherlamza.data.models.WeatherData
import com.example.weatherlamza.databinding.ItemWeatherBinding
import com.example.weatherlamza.utils.extensions.currentDate
import com.example.weatherlamza.utils.extensions.toNamedDayOfTheWeek

class DailyWeatherForecastAdapter :
    RecyclerView.Adapter<DailyWeatherForecastAdapter.DailyWeatherForecastViewHolder>() {

    private var _binding: ItemWeatherBinding? = null
    private val binding: ItemWeatherBinding get() = _binding!!

    var dailyWeatherForecast = emptyList<WeatherData>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DailyWeatherForecastViewHolder {
        _binding = ItemWeatherBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DailyWeatherForecastViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DailyWeatherForecastViewHolder, position: Int) {
        holder.weatherData = dailyWeatherForecast[position]
    }

    override fun getItemCount(): Int = dailyWeatherForecast.size

    inner class DailyWeatherForecastViewHolder(private val binding: ItemWeatherBinding) :
        RecyclerView.ViewHolder(binding.root) {

        var weatherData: WeatherData? = null
            set(value) {
                field = value
                if (value != null) {
                    with(binding) {
                        dayOfTheWeek.text = value.currentDate.dayOfWeek.toNamedDayOfTheWeek()
                        humidity.text = value.temperature.humidity.toInt().toString()
                        temperatureHigh.text = "${value.temperature.tempMax.toInt()} °C"
                        temperatureLow.text = "${value.temperature.tempMin.toInt()} °C"
                    }
                }
            }
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        _binding = null
    }
}
