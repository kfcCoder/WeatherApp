package com.example.weatherapp.api

import com.example.weatherapp.util.Constants.API_KEY
import com.example.weatherforecastapp.model.current.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * fetch data from remote api
 */
interface WeatherApi {

    /**
     * [] suspend fun
     * [] @GET annotation for sub-url
     * [] pass query in ctor(value needs to be the same as doc)
     * [] return response
     */
    @GET("/current")
    suspend fun getCurrentWeather( // https://api.weatherstack.com/current?access_key=[]&query=New%20York
        @Query("query") location: String,
        @Query("units") units: String = "m",
        @Query("access_key") apiKey: String = API_KEY
    ): WeatherResponse




}