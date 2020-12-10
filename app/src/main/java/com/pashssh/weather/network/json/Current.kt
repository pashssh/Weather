package com.pashssh.weather.network.json

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
    val wind_speed: Double,
    val wind_deg: Int,
    val rain: RainCurrent?,
    val snow: SnowCurrent?,
    val weather: List<WeatherCurrent>
)

