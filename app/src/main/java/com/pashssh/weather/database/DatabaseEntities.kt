package com.pashssh.weather.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.pashssh.weather.domain.DomainWeatherData
import com.pashssh.weather.domain.DomainWeatherDaily
import com.pashssh.weather.domain.DomainWeatherHourly


@Entity
data class DatabaseWeatherData constructor(
    val time: Int,
    val temperature: Int,
    val maxTemp: Int,
    val minTemp: Int,
    val latitude: Double,
    val longitude: Double,
    @PrimaryKey
    val location: String,
    val timezone: String,
    val feelsLike: Int,
    val cloudsDescription: String,
    val weatherHourlyWeather: List<DatabaseWeatherHourly>,
    val weatherDailyWeather: List<DatabaseWeatherDaily>
)

data class DatabaseWeatherHourly(
    val time: Int,
    val temperature: Int,
    val imageId: Int,
    val iconIdWithUrl: String
)

data class DatabaseWeatherDaily(
    val time: Int,
    val minTemp: Int,
    val maxTemp: Int,
    val imageId: Int,
    val iconIdWithUrl: String
)


fun DatabaseWeatherData.asDomainModel(): DomainWeatherData {
    return DomainWeatherData(
        temperature = this.temperature,
        minTemp = this.minTemp,
        maxTemp = this.maxTemp,
        location = this.location,
        feelsLike = this.feelsLike,
        cloudsDescription = this.cloudsDescription,
        listWeatherHourly = this.weatherHourlyWeather.map { hourly ->
            return@map DomainWeatherHourly(
                time = hourly.time,
                temperature = hourly.temperature,
                imageId = hourly.imageId
            )
        },
        listWeatherDaily = this.weatherDailyWeather.map { daily ->
            return@map DomainWeatherDaily(
                time = daily.time,
                minTemp = daily.minTemp,
                maxTemp = daily.maxTemp,
                imageId = daily.imageId
            )
        }
    )
}













