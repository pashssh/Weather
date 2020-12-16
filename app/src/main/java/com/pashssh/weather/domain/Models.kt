package com.pashssh.weather.domain

import com.pashssh.weather.database.DatabaseWeatherData
import java.text.SimpleDateFormat
import java.util.*


data class DomainWeatherData(
    val temperature: String,
    val dayTemp: String,
    val location: String,
    val feelsLike: String,
    val description: String,
    val listWeatherHourly: List<DomainWeatherHourly>,
    val listWeatherDaily: List<DomainWeatherDaily>
)

data class DomainWeatherHourly(
    val time: String,
    val temperature: String,
    val imageId: Int,
)

data class DomainWeatherDaily(
    val time: String,
    val dayTemp: String,
    val imageId: Int
)



