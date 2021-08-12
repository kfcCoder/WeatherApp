package com.example.weatherforecastapp.model.current

import com.google.gson.annotations.SerializedName

data class WeatherResponse(
        @SerializedName("current")
        val currentWeather: CurrentWeather,
        @SerializedName("location")
        val weatherLocation: WeatherLocation,
        val request: Request
)