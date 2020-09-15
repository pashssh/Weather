package com.pashssh.weather.network.json

data class WeatherCurrent(
    val description: String,
    val icon: String,
    val id: Int,
    val main: String
)