package com.pashssh.weather.network.json

import androidx.room.ColumnInfo

data class Current(
    val dt: Int,
    val sunrise: Int,
    val sunset: Int,
    val temp: Double,
    val feels_like: Double,
    val pressure: Int,
    val humidity: Int,
    val clouds: Int,
    val uvi: Double,
    val visibility: Int,
    @ColumnInfo(name = "full_name")
    val windSpeed: Double,
    @ColumnInfo(name = "full_name")
    val windDeg: Int,
    val rain: RainCurrent?,
    val snow: SnowCurrent?,
    val weather: List<WeatherCurrent>
)

