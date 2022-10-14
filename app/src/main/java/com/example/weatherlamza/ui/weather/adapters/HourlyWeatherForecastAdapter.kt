package com.example.weatherlamza.ui.weather.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherlamza.data.models.WeatherData
import com.example.weatherlamza.databinding.ItemWeatherHourlyBinding
import com.example.weatherlamza.utils.extensions.getHoursFromLocalTime

class HourlyWeatherForecastAdapter :
    RecyclerView.Adapter<HourlyWeatherForecastAdapter.HourlyWeatherForecastViewHolder>() {

    private var _binding: ItemWeatherHourlyBinding? = null
    private val binding: ItemWeatherHourlyBinding get() = _binding!!

    var hourlyWeatherForecast = emptyList<WeatherData>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HourlyWeatherForecastViewHolder {
        _binding =
            ItemWeatherHourlyBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HourlyWeatherForecastViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HourlyWeatherForecastViewHolder, position: Int) {
        holder.weatherData = hourlyWeatherForecast[position]
    }

    override fun getItemCount(): Int = hourlyWeatherForecast.size

    inner class HourlyWeatherForecastViewHolder(private val binding: ItemWeatherHourlyBinding) :
        RecyclerView.ViewHolder(binding.root) {

        var weatherData: WeatherData? = null
            set(value) {
                field = value
                if (value != null) {
                    with(binding) {
                        hour.text = value.dt.toString().getHoursFromLocalTime()
                        temperature.text = "${value.temperature.temperature} °"
                    }
                }
            }
    }
}
