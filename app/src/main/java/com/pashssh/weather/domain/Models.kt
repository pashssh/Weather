package com.pashssh.weather.domain


data class DomainWeatherData (
    val temperature: Int,
    val minTemp: Int,
    val maxTemp: Int,
    val location: String,
    val feelsLike: Int,
    val cloudsDescription: String,
    val listWeatherHourly: List<DomainWeatherHourly>,
    val listWeatherDaily: List<DomainWeatherDaily>
)

data class DomainWeatherHourly(
    val time: Int,
    val temperature: Int,
    val imageId: Int,
)

data class DomainWeatherDaily(
    val time: Int,
    val minTemp: Int,
    val maxTemp: Int,
    val imageId: Int
)

