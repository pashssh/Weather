package com.pashssh.weather.network.json

import com.pashssh.weather.database.DatabaseCurrent
import com.pashssh.weather.database.DatabaseDaily
import com.pashssh.weather.database.DatabaseHourly


data class Weather(
    val current: Current,
    val daily: List<Daily>,
    val hourly: List<Hourly>,
    val lat: Double,
    val lon: Double,
    val timezone: String,
    val timezone_offset: Int?,
)


data class HourlyList (
    val time: Int,
    val temperature: Int,
    val imageId: Int

)


fun Weather.asCurrentDatabaseModel(): DatabaseCurrent {
    return DatabaseCurrent(
        time = this.current.dt,
        temperature = this.current.temp.toInt(),
        maxTemp = this.daily[0].temp.max.toInt(),
        minTemp = this.daily[0].temp.min.toInt(),
        feelsLike = this.current.feels_like.toInt(),
        cloudsDescription = this.current.weather[0].description,
        latitude = this.lat,
        longitude = this.lon,
        location = this.timezone,
        hourlyWeather = this.hourly.chunked(24)[0].map {
            return@map DatabaseHourly(
                time = it.dt,
                temperature = it.temp.toInt(),
                imageId = it.weather[0].id,
                iconIdWithUrl = it.weather[0].icon
            )
        },
        dailyWeather = this.daily.chunked(5)[0].map {
            return@map DatabaseDaily(
                time = it.dt,
                minTemp = it.temp.min.toInt(),
                maxTemp = it.temp.max.toInt(),
                imageId = it.weather[0].id,
                iconIdWithUrl = it.weather[0].icon
            )
        }
    )
}




