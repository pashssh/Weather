package com.pashssh.weather.network.json

data class WeatherHourly(
    val description: String,
    val icon: String,
    val id: Int,
    val main: String
)