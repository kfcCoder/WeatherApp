package com.example.weatherapp.repository

import androidx.lifecycle.LiveData
import com.example.weatherapp.data_source.WeatherNetworkDataSource
import com.example.weatherapp.db.CurrentWeatherDao
import com.example.weatherapp.db.WeatherLocationDao
import com.example.weatherforecastapp.model.current.CurrentWeather
import com.example.weatherforecastapp.model.current.WeatherLocation
import com.example.weatherforecastapp.model.current.WeatherResponse
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.threeten.bp.ZonedDateTime
import javax.inject.Inject

/**
 * 1. first launch #WeatherResponse will be null
 * 2. #WeatherResonse will be fetched when @getCurrentWeather() invoked by ViewModel
 *
 *
 */
class WeatherRepositoryImpl @Inject constructor (
        private val currentWeatherDao: CurrentWeatherDao,
        private val weatherLocationDao: WeatherLocationDao,
        private val weatherNetworkDataSource: WeatherNetworkDataSource,
        //private val locationProvider: LocationProvider
) : WeatherRepository {

    // observe weatherDataSource when repo instantiate
    init {
        weatherNetworkDataSource.downloadedWeatherResponseLive.observeForever {
            persistFetchedCurrentWeather(it) // if a new weather is fetched, then cache it in db
        }
    }

    // persist newly fetched current weather to db
    private fun persistFetchedCurrentWeather(fetchedWeather: WeatherResponse) {
        GlobalScope.launch(IO) {
            currentWeatherDao.upsert(fetchedWeather.currentWeather)
            weatherLocationDao.upsert(fetchedWeather.weatherLocation)
        }
    }

    // init weather data and return LiveData in db to ViewModel
    override suspend fun getCurrentWeather(isMetric: Boolean): LiveData<CurrentWeather> {
        return withContext(IO) {
            //initWeatherData(isMetric)
            fetchCurrentWeather(isMetric)
            currentWeatherDao.getWeather() // in order to work sequentially
        }
    }

    /**
     *  dao to return [LiveData] on main thread is working as well !!
    override suspend fun getCurrentWeather2(isMetric: Boolean): LiveData<CurrentWeather> {
        withContext(IO) {
            fetchCurrentWeather(isMetric)
        }
        return currentWeatherDao.getWeather()
    }*/

    // determine is fetch data needed or not (TBD)
    private suspend fun initWeatherData(isMetric: Boolean) {
        val lastWeatherLocation = weatherLocationDao.getLocation().value

        /*if (isFetchedCurrentNeeded(ZonedDateTime.now().minusHours(1))) {
            fetchCurrentWeather(isMetric)
        }*/

        if (lastWeatherLocation == null)  // i.e. first time launch app, nothing in db
                //|| locationProvider.hasLocationChanged(lastWeatherLocation))
        {
            fetchCurrentWeather(isMetric)
            return
        }

        /*if (isFetchedCurrentNeeded(lastWeatherLocation.zonedDateTime)) {
            fetchCurrentWeather(isMetric)
        }*/
    }

    // fetch current weather from data source (persist in db as well)
    private suspend fun fetchCurrentWeather(isMetric: Boolean) {
        val metric = if (isMetric) "m" else "f"
        weatherNetworkDataSource.fetchCurrentWeather(
                //locationProvider.getPreferredLocationString(),
                "Taipei",
                metric
        )
    }

    /**
     *  Queried Weather */
    override suspend fun fetchWeatherByQuery(q: String) {
        weatherNetworkDataSource.fetchCurrentWeather(
                q,
                "m"
        )
    }

    // work on main thread
    override fun getWeatherByQuery(): LiveData<CurrentWeather> {
          return  currentWeatherDao.getWeather()
    }

    // combine fetch data by query and return liveData
    override suspend fun fetchAndGetWeatherCombined(q: String): LiveData<CurrentWeather>  {
        return withContext(IO) {
            fetchWeatherByQuery(q)
            getWeatherByQuery()
        }
    }

    /**
     *  Weather Location */
    // get weatherLocation in db while fetch data from api
    override suspend fun getWeatherLocation(): LiveData<WeatherLocation> {
        return withContext(IO) {
            weatherLocationDao.getLocation()
        }
    }







    // 'true' if lastFetchTime is 30 mins ago
    /*private fun isFetchedCurrentNeeded(lastFetchTime: ZonedDateTime): Boolean {
        val thirtyMinutesAgo = ZonedDateTime.now().minusMinutes(30)
        return lastFetchTime.isBefore(thirtyMinutesAgo)
    }*/


}