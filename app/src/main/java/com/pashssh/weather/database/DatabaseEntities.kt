package com.pashssh.weather.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.pashssh.weather.domain.DomainCurrent
import com.pashssh.weather.domain.DomainDaily
import com.pashssh.weather.domain.DomainHourly
import com.pashssh.weather.network.json.HourlyList


@Entity
data class DatabaseCurrent constructor(
    val time: Int,
    val temperature: Int,
    val maxTemp: Int,
    val minTemp: Int,
    @PrimaryKey
    val location: String,
    val feelsLike: Int,
    val cloudsDescription: String,
    val hourlyWeather: List<DatabaseHourly>,
    val dailyWeather: List<DatabaseDaily>
)

data class DatabaseHourly(
    val time: Int,
    val temperature: Int,
    val imageId: Int,
    val iconIdWithUrl: String
)

data class DatabaseDaily(
    val time: Int,
    val minTemp: Int,
    val maxTemp: Int,
    val imageId: Int,
    val iconIdWithUrl: String
)

fun DatabaseCurrent.asDomainModel(): DomainCurrent {
    return DomainCurrent(
        temperature = this.temperature,
        minTemp = this.minTemp,
        maxTemp = this.maxTemp,
        location = this.location,
        feelsLike = this.feelsLike,
        cloudsDescription = this.cloudsDescription,
        listHourly = this.hourlyWeather.map { hourly ->
            return@map DomainHourly(
                time = hourly.time,
                temperature = hourly.temperature,
                imageId = hourly.imageId
            )
        },
        listDaily = this.dailyWeather.map { daily ->
            return@map DomainDaily(
                time = daily.time,
                minTemp = daily.minTemp,
                maxTemp = daily.maxTemp,
                imageId = daily.imageId
            )
        }
    )
}













