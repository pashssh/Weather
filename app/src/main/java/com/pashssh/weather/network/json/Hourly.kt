package com.pashssh.weather.network.json

data class Hourly(
    val dt: Int,
    val temp: Double,
    val weather: List<WeatherHourly>,
)