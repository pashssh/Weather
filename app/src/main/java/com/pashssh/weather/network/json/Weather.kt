package com.pashssh.weather.network.json

import com.pashssh.weather.database.DatabaseWeatherData
import com.pashssh.weather.database.DatabaseWeatherDaily
import com.pashssh.weather.database.DatabaseWeatherHourly


data class Weather(
    val current: Current,
    val lat: Double,
    val lon: Double,
    val timezone: String,
    val hourly: List<Hourly>,
    val daily: List<Daily>,
)





