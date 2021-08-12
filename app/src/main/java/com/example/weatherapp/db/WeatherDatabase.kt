package com.example.weatherapp.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.weatherforecastapp.model.current.CurrentWeather
import com.example.weatherforecastapp.model.current.WeatherLocation


/**
 * Dao holder
 */
@Database(
        entities = [CurrentWeather::class,
                    WeatherLocation::class],
        version = 1,
        exportSchema = false
)

@TypeConverters(Converters::class)
abstract class WeatherDatabase : RoomDatabase() {
    abstract fun getCurrentWeatherDao(): CurrentWeatherDao
    abstract fun getWeatherLocationDao(): WeatherLocationDao

    // Dagger will take care of the rest house keeping stuff...


}