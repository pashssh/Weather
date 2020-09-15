package com.pashssh.weather.network.json

import com.pashssh.weather.database.DatabaseDaily
import com.pashssh.weather.database.DatabaseCurrent
import com.pashssh.weather.database.DatabaseHourly


data class Weather(
    val current: Current,
    val daily: List<Daily>,
    val hourly: List<Hourly>,
    val lat: Double,
    val lon: Double,
    val timezone: String,
    val timezone_offset: Int?
)


fun Weather.asCurrentDatabaseModel(): DatabaseCurrent {
    return DatabaseCurrent(
        temperature = this.current.temp.toInt(),
        maxTemp = this.daily[0].temp.max.toInt(),
        minTemp = this.daily[0].temp.min.toInt(),
        feelsLike = this.current.feels_like.toInt(),
        cloudsDescription = this.current.weather[0].description,
        location = this.timezone
    )
}

fun Weather.asHourlyDatabaseModel(): Array<DatabaseHourly> {
    return this.hourly.map {
        DatabaseHourly(
            time = it.dt,
            location = this.timezone,
            temperature = it.temp.toInt(),
            cloudsDescription = it.weather[0].description
        )
    }.toTypedArray()
}

fun Weather.asDailyDatabaseModel(): Array<DatabaseDaily> {
    return this.daily.map {
        DatabaseDaily(
            time = it.dt,
        location = this.timezone,
        minTemp = it.temp.min.toInt(),
        maxTemp = it.temp.max.toInt(),
        statusImage = it.weather[0].icon
        )
    }.toTypedArray()
}



