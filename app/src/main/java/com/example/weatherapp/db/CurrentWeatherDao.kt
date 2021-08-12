package com.example.weatherapp.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.weatherapp.util.Constants.CURRENT_WEATHER_ID
import com.example.weatherforecastapp.model.current.CurrentWeather

/**
 * manipulation over #CurrentWeather
 */
@Dao
interface CurrentWeatherDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(entry: CurrentWeather): Long

    @Query("SELECT * FROM current_weather WHERE id = ${CURRENT_WEATHER_ID}")
    fun getWeather(): LiveData<CurrentWeather>
}