package com.pashssh.weather.network.json

import com.pashssh.weather.database.DatabaseCurrent

data class Current(
    val clouds: Int,
    val dew_point: Double,
    val dt: Int,
    val feels_like: Double,
    val humidity: Int,
    val pressure: Int,
    val rain: RainCurrent?,
    val sunrise: Int,
    val sunset: Int,
    val temp: Double,
    val uvi: Double,
    val visibility: Int,
    val weather: List<WeatherCurrent>,
    val wind_deg: Int,
    val wind_speed: Double?
)

