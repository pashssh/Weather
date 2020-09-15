package com.pashssh.weather.network.json

data class Hourly(
    val clouds: Int,
    val dew_point: Double,
    val dt: Int,
    val feels_like: Double,
    val humidity: Int,
    val pop: Double,
    val pressure: Int,
    val rain: RainHourly?,
    val temp: Double,
    val visibility: Int,
    val weather: List<WeatherHourly>,
    val wind_deg: Int,
    val wind_speed: Double
)