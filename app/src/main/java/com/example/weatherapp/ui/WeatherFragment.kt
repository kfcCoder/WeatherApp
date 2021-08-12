package com.example.weatherapp.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView

import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.liveData
import androidx.navigation.fragment.findNavController
import com.example.weatherapp.R
import com.example.weatherapp.databinding.FragmentWeatherBinding
import com.example.weatherapp.util.Constants.TAG
import com.example.weatherapp.viewmodel.WeatherViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 *
 * #getCurrentLocation
 *                     ->
 * #searchForLocation
 *
 *
 * 1. default to current location (hide btFindMyLocation)
 * 2. show btFindMyLocation when use searched location
 *
 */
@AndroidEntryPoint
class WeatherFragment : Fragment() {
    private val viewModel by viewModels<WeatherViewModel>()
    private lateinit var binding: FragmentWeatherBinding



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_weather, container, false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvLocation.text = "HaHa"

        binding.btDetails.setOnClickListener {
            findNavController().navigate(R.id.action_weatherFragment_to_detailsFragment)
        }

        binding.btFindMyLocation.setOnClickListener {
            getCurrentWeather()
        }


        binding.svSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                /**
                 * method 3
                 */
                changeQuery(query!!)

                /**
                 * method2
                 */
                viewModel.changeQuery(query!!)

                /**
                 * method 1
                 * fetch new data for query
                 * 先搜尋再返回LiveData(而非同時進行)
                 */
                lifecycleScope.launch {
                    query?.let { viewModel.fetchWeatherByQuery(it) } // 搜尋
                    observeWeatherForLocation() // 獲得LiveData並觀察

                }



                binding.svSearch.clearFocus()  // close keyboard
                return true
            }

            // return 'true' if the query has been handled by the listener,
            // 'false' to let the SearchView perform the default action.
            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })

        //observeQueryWeather()
        observe()
    }




    fun observeWeatherForLocation() {
        viewModel.getWeatherByQuery().observe(viewLifecycleOwner) {
            if (it == null) return@observe
            Log.e(TAG, "searchWeatherForLocation: $it")
        }
    }

    fun observeQueryWeather() {
        viewModel.queryWeatherLive.observe(viewLifecycleOwner) {
            Log.e(TAG, "observeQueryWeather: $it")
        }
    }

    /**
     *
     */

    fun changeQuery(q: String) {
        viewModel.setNewQuery(q)
    }

    fun observe(){
        viewModel.queryWeatherLive.observe(viewLifecycleOwner) {
            Log.e(TAG, "zzz: $it")
        }
    }

    /**
     *
     */
    fun getCurrentWeather() {
        lifecycleScope.launch {
            /** [CHANGE] no async and await */
            val deferredCurrentWeather = async { viewModel.getCurrentWeather() }
            val currentWeatherLive = deferredCurrentWeather.await()

            val deferredLocation = async { viewModel.getWeatherLocation() }
            val locationLive = deferredLocation.await()

            withContext(Main) {
                locationLive.observe(viewLifecycleOwner) {
                    if (it == null) return@observe
                    binding.tvLocation.text = it.name
                }


                currentWeatherLive.observe(viewLifecycleOwner) {
                    if (it == null) return@observe
                    binding.tvDescription.text = it.weather_descriptions[0]
                    binding.tvTemperature.text = "${it.temperature}°C"
                    binding.tvFeelsLike.text = "${it.feelslike}°C"

                }
            }

        }
    }
}