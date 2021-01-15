package com.pashssh.weather.repository

import com.pashssh.weather.database.DatabaseWeatherDaily
import com.pashssh.weather.database.DatabaseWeatherData
import com.pashssh.weather.database.DatabaseWeatherHourly
import com.pashssh.weather.database.WeatherDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CityChangeRepository(private val weatherDatabase: WeatherDatabase) {


    fun insertChangedCity(lat: Double, lon: Double, name: String) {
            weatherDatabase.weatherDao.insertCity(
                DatabaseWeatherData(
                    0,
                    0,
                    0,
                    0,
                    lat,
                    lon,
                    name,
                    "null",
                    0,
                    "null",
                    listOf(DatabaseWeatherHourly(0, 0, 0, "04d")),
                    listOf(DatabaseWeatherDaily(0, 0, 0, 0, "04d"))
                )
            )
        }
}