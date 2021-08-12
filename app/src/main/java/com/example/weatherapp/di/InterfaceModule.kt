package com.example.weatherapp.di


import com.example.weatherapp.data_source.WeatherNetworkDataSource
import com.example.weatherapp.data_source.WeatherNetworkDataSourceImpl
import com.example.weatherapp.repository.WeatherRepository
import com.example.weatherapp.repository.WeatherRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * 告訴Hilt如和實例化interface
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class InterfaceModule {
    // @Binds 注释会告知 Hilt 在需要提供接口的实例时要使用哪种实现
    @Binds
    abstract fun bindWeatherNetworkDataSource(
        impl: WeatherNetworkDataSourceImpl
    ): WeatherNetworkDataSource


    @Binds
    abstract fun bindForecastRepository(
        impl: WeatherRepositoryImpl
    ): WeatherRepository

    /*@Binds
    abstract fun bindUnitProvider(
        impl: UnitProviderImpl
    ): UnitProvider*/

    /*@Binds
    abstract fun bindLocationProvider(
        impl: LocationProviderImpl
    ): LocationProvider*/





}