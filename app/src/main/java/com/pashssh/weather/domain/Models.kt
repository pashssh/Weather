package com.pashssh.weather.domain


data class DomainCurrent (
    val temperature: Int,
    val maxTemp: Int,
    val minTemp: Int,
    val location: String,
    val feelsLike: Int,
    val cloudsDescription: String
)

data class DomainHourly(
    val time: Int,
    val location: String,
    val temperature: Int,
    val cloudsDescription: String,
)

data class DomainDaily(
    val time: Int,
    val minTemp: Int,
    val maxTemp: Int,
    val statusImage: String
)

