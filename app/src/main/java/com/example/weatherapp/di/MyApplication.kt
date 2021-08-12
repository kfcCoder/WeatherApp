package com.example.weatherapp.di

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen
import dagger.hilt.android.HiltAndroidApp

/**
 * make #Application injectable
 */
@HiltAndroidApp
class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
    }
}