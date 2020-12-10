package com.pashssh.weather.network.json

import com.pashssh.weather.database.DatabaseWeatherData
import com.pashssh.weather.database.DatabaseWeatherDaily
import com.pashssh.weather.database.DatabaseWeatherHourly


data class Weather(
    val current: Current,
    val lat: Double,
    val lon: Double,
    val timezone: String,
    val hourly: List<Hourly>,
    val daily: List<Daily>,
)



fun Weather.asDatabaseModel(city: String): DatabaseWeatherData {
    return DatabaseWeatherData(
        time = this.current.dt,
        temperature = this.current.temp.toInt(),
        maxTemp = this.daily[0].temp.max.toInt(),
        minTemp = this.daily[0].temp.min.toInt(),
        feelsLike = this.current.feels_like.toInt(),
        cloudsDescription = this.current.weather[0].description,
        latitude = this.lat,
        longitude = this.lon,
        location = city,
        timezone = this.timezone,
        weatherHourlyWeather = this.hourly.chunked(24)[0].map {
            return@map DatabaseWeatherHourly(
                time = it.dt,
                temperature = it.temp.toInt(),
                imageId = it.weather[0].id,
                iconIdWithUrl = it.weather[0].icon
            )
        },
        weatherDailyWeather = this.daily.chunked(5)[0].map {
            return@map DatabaseWeatherDaily(
                time = it.dt,
                minTemp = it.temp.min.toInt(),
                maxTemp = it.temp.max.toInt(),
                imageId = it.weather[0].id,
                iconIdWithUrl = it.weather[0].icon
            )
        }
    )
}




