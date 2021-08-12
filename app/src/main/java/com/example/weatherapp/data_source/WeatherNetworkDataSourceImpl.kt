package com.example.weatherapp.data_source

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.weatherapp.api.WeatherApi
import com.example.weatherapp.util.Constants.TAG
import com.example.weatherforecastapp.model.current.WeatherResponse
import javax.inject.Inject

/**
 * fetching data from network and handling exception when no internet connection available
 */
class WeatherNetworkDataSourceImpl @Inject constructor (
        private val api: WeatherApi
) : WeatherNetworkDataSource {

    private val _downloadedWeatherResponseLive = MutableLiveData<WeatherResponse>()
    override val downloadedWeatherResponseLive: LiveData<WeatherResponse>
        = _downloadedWeatherResponseLive


    override suspend fun fetchCurrentWeather(
            location: String,
            units: String) {
        try {
            val currentWeather = api
                    .getCurrentWeather(location, units)
            _downloadedWeatherResponseLive.postValue(currentWeather)
        } catch  (e: Exception) {
            Log.e(TAG, "No internet connection.", e)
        }
    }
}