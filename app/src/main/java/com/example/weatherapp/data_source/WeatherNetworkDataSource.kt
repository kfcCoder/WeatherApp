package com.example.weatherapp.data_source

import androidx.lifecycle.LiveData
import com.example.weatherforecastapp.model.current.WeatherResponse

/**
 * abstraction for remote data source
 */
interface WeatherNetworkDataSource {
    val downloadedWeatherResponseLive: LiveData<WeatherResponse>

    suspend fun fetchCurrentWeather(
        location: String,
        units: String = "m"
    )


}