package com.example.weatherapp.providers

import com.example.weatherforecastapp.model.current.WeatherLocation

interface LocationProvider {

    suspend fun getPreferredLocationString(): String
    suspend fun hasLocationChanged(lastWeatherLocation: WeatherLocation): Boolean
}