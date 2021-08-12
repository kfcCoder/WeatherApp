package com.example.weatherapp.viewmodel

import androidx.lifecycle.*
import com.example.weatherapp.repository.WeatherRepository
import com.example.weatherforecastapp.model.current.CurrentWeather
import com.example.weatherforecastapp.model.current.WeatherLocation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * 1. why separate fetch and getLiveData operation?
 * ans: avoid deferred operation
 */
@HiltViewModel
class WeatherViewModel @Inject constructor (
        private val repo: WeatherRepository,
) : ViewModel() {

    /*private val unitSystem: UnitSystem
        = unitProvider.getUnitSystem()*/


    private val isMetric: Boolean = true


    suspend fun getCurrentWeather(): LiveData<CurrentWeather> {
        return repo.getCurrentWeather(isMetric)
    }



    suspend fun getWeatherLocation(): LiveData<WeatherLocation> {
        return repo.getWeatherLocation()
    }


    /**
     * Queried Weather */
    suspend fun fetchWeatherByQuery(q: String) {
        repo.fetchWeatherByQuery(q)
    }

    fun getWeatherByQuery(): LiveData<CurrentWeather> {
        return repo.getWeatherByQuery()
    }

    suspend fun fetchAndGetWeatherCombined(q: String): LiveData<CurrentWeather> {
        return repo.fetchAndGetWeatherCombined(q)
    }



    /**
     * [Try] switchMap
     */

    val queryLive = MutableLiveData<String>()

    fun setNewQuery(q: String) {
        queryLive.postValue(q)
    }

    val queryWeatherSwitchedLive = queryLive.switchMap { query ->
        liveData {
            val data = repo.fetchAndGetWeatherCombined(query).value
            emit(data)
        }
    }

    /**
     * works!!
     */
    val queryWeatherLive = MutableLiveData<CurrentWeather>()

    fun changeQuery(q: String) {
        viewModelScope.launch {
            val newWeatherLive = repo.fetchAndGetWeatherCombined(q)
            withContext(Main) {
                newWeatherLive.observeForever {
                    queryWeatherLive.postValue(it)
                }
            }

        }

    }





}

