package com.example.weatherapp.di

import android.annotation.SuppressLint
import android.content.Context
import androidx.room.Room
import com.example.weatherapp.api.WeatherApi
import com.example.weatherapp.db.CurrentWeatherDao
import com.example.weatherapp.db.WeatherDatabase
import com.example.weatherapp.db.WeatherLocationDao
import com.example.weatherapp.util.Constants
import com.example.weatherapp.util.Constants.WEATHER_DATABASE_NAME
import com.google.android.gms.location.FusedLocationProviderClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * manual to tell dagger how to instantiate dependencies
 */
@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    /** Db **/
    @Singleton
    @Provides
    fun provideForecastDatabase(@ApplicationContext app: Context): WeatherDatabase
        = Room.databaseBuilder(
            app,
            WeatherDatabase::class.java,
            WEATHER_DATABASE_NAME
        ).build()

    @Singleton
    @Provides
    fun provideCurrentWeatherDao(db: WeatherDatabase): CurrentWeatherDao
        = db.getCurrentWeatherDao()

    /** different kinds of Providers **/
    @Singleton
    @Provides
    fun provideWeatherLocationDao(db: WeatherDatabase): WeatherLocationDao
        = db.getWeatherLocationDao()

    /** Location info **/
    @SuppressLint("VisibleForTests")
    @Singleton
    @Provides
    fun provideFusedLocationProviderClient(
            @ApplicationContext app: Context
    ) = FusedLocationProviderClient(app)


    /** Api **/
    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideWeatherStackApi(retrofit: Retrofit): WeatherApi =
        retrofit.create(WeatherApi::class.java)


    @Provides
    @Singleton
    fun provideContext(@ApplicationContext app: Context): Context
        = app



}