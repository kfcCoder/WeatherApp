package com.example.weatherapp.repository

import androidx.lifecycle.LiveData
import com.example.weatherforecastapp.model.current.CurrentWeather
import com.example.weatherforecastapp.model.current.WeatherLocation


interface WeatherRepository {
    suspend fun getCurrentWeather(isMetric: Boolean): LiveData<CurrentWeather>
    //suspend fun getCurrentWeather2(isMetric: Boolean): LiveData<CurrentWeather>

    suspend fun fetchWeatherByQuery(q: String)
    fun getWeatherByQuery(): LiveData<CurrentWeather>
    suspend fun fetchAndGetWeatherCombined(q: String): LiveData<CurrentWeather>  // 合併上兩者

    suspend fun getWeatherLocation(): LiveData<WeatherLocation>
}