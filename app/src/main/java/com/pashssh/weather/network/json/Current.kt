package com.pashssh.weather.network.json

import androidx.room.ColumnInfo
import com.squareup.moshi.Json

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
    @Json(name = "wind_speed")
    val windSpeed: Double,
    @Json(name = "wind_deg")
    val windDeg: Int,
    val rain: RainCurrent?,
    val snow: SnowCurrent?,
    val weather: List<WeatherCurrent>
)

