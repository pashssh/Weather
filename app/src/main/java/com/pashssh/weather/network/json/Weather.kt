package com.pashssh.weather.network.json

import com.google.gson.Gson
import com.pashssh.weather.database.DatabaseCurrent


data class Weather(
    val current: Current,
    val daily: List<Daily>,
    val hourly: List<Hourly>,
    val lat: Double,
    val lon: Double,
    val timezone: String,
    val timezone_offset: Int?
)


fun Weather.asDatabaseModel(): DatabaseCurrent {
    return DatabaseCurrent(
        temperature = this.current.temp.toInt(),
        maxTemp = this.daily[0].temp.max.toInt(),
        minTemp = this.daily[0].temp.min.toInt(),
        feelsLike = this.current.feels_like.toInt(),
        cloudsDescription = this.current.weather[0].description,
        location = this.timezone,
        hourlyWeather = this.toJsonStringHourly(),
        dailyWeather = this.toJsonStringDaily()
//        hourlyList = hourly.map {
//            DatabaseHourly(
//                temperature = it.temp.toInt(),
//                cloudsDescription = it.weather[0].description
//            )
//        }
    )
}

fun Weather.toJsonStringHourly(): String {
    val list = this.hourly.map {
        HourlyWeather(it.dt, this.timezone, it.temp.toInt(), it.weather[0].description)
    }
    return Gson().toJson(list)
}

fun Weather.toJsonStringDaily(): String {
    val list = this.daily.map {
        DailyWeather(it.dt, it.temp.min.toInt(), it.temp.max.toInt(), it.weather[0].icon)
    }
    return Gson().toJson(list)
}


data class HourlyWeather(
    val time: Int,
    val location: String,
    val temperature: Int,
    val cloudsDescription: String,
)

data class DailyWeather(
    val time: Int,
    val minTemp: Int,
    val maxTemp: Int,
    val statusImage: String
)