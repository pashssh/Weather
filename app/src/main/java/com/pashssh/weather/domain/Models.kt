package com.pashssh.weather.domain

import com.google.gson.Gson


data class WeatherDomain (
    val temperature: Int,
    val maxTemp: Int,
    val minTemp: Int,
    val location: String,
    val feelsLike: Int,
    val cloudsDescription: String,
    val hourlyWeather: List<HourlyDomain>,
    val dailyWeather: List<DailyDomain>
)

data class HourlyDomain(
    val time: Int,
    val location: String,
    val temperature: Int,
    val cloudsDescription: String,
)

data class DailyDomain(
    val time: Int,
    val minTemp: Int,
    val maxTemp: Int,
    val statusImage: String
)

