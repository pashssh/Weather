package com.pashssh.weather.domain


data class DomainCurrent (
    val temperature: Int,
    val minTemp: Int,
    val maxTemp: Int,
    val location: String,
    val feelsLike: Int,
    val cloudsDescription: String,
    val listHourly: List<DomainHourly>,
    val listDaily: List<DomainDaily>
)

data class DomainHourly(
    val time: Int,
    val temperature: Int,
    val imageId: Int,
)

data class DomainDaily(
    val time: Int,
    val minTemp: Int,
    val maxTemp: Int,
    val imageId: Int
)

