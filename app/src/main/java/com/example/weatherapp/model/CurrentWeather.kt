package com.example.weatherforecastapp.model.current

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.weatherapp.util.Constants.CURRENT_WEATHER_ID

/**
 * create table "current_weather" in room db
 */
@Entity(tableName = "current_weather")
data class  CurrentWeather (
    val cloudcover: Int,
    val feelslike: Int,
    val humidity: Int,
    val is_day: String,
    val observation_time: String,
    val precip: Double, // mm
    val pressure: Int,
    val temperature: Int,
    val uv_index: Int,
    val visibility: Int,
    val weather_code: Int,
    val weather_descriptions: List<String>,
    val weather_icons: List<String>,
    val wind_degree: Int,
    val wind_dir: String,
    val wind_speed: Int
) {
    @PrimaryKey(autoGenerate = false) // bcs we only want to preserve 1 up-to-date data in db
    var id: Int = CURRENT_WEATHER_ID
}