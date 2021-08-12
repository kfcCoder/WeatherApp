package com.example.weatherapp.retrofit

import com.example.weatherapp.api.WeatherApi
import com.example.weatherapp.util.Constants.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * create api connection by Retrofit
 */
object WeatherRetrofit {

    private val retrofit by lazy {
        Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }

    val API: WeatherApi by lazy {
        retrofit.create(WeatherApi::class.java)
    }
}