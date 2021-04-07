package com.pashssh.weather.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.pashssh.weather.domain.DomainWeatherData
import com.pashssh.weather.domain.DomainWeatherDaily
import com.pashssh.weather.domain.DomainWeatherHourly
import kotlinx.serialization.Serializable
import java.text.SimpleDateFormat
import java.util.*


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
    val uvi: Double,
    val humidity: Int,
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


@Entity
data class LocationItem(
    @ColumnInfo(name = "location")
    @PrimaryKey
    val cityName: String,
    @ColumnInfo(name = "latitude")
    val latitude: Double,
    @ColumnInfo(name = "longitude")
    val longitude: Double
) : java.io.Serializable

fun DatabaseWeatherData.asDomainModel(): DomainWeatherData {

    val sdfHourly = SimpleDateFormat("HH:mm")
    val sdfDaily = SimpleDateFormat("E, dd MMM")

    return DomainWeatherData(
        temperature = "${this.temperature}\u00B0",
        dayTemp = "${this.minTemp}\u00B0 / ${this.maxTemp}\u00B0",
        location = this.location,
        feelsLike = " ${this.feelsLike}\u00B0",
        uvi = " ${this.uvi}",
        humidity = " ${this.humidity}%",
        description = this.cloudsDescription,
        listWeatherHourly = this.weatherHourlyWeather.map {
            return@map DomainWeatherHourly(
                time = sdfHourly.format(Date(it.time.toLong() * 1000)),
                temperature = "${it.temperature}\u00B0",
                iconWithUrl = it.iconIdWithUrl
            )
        },
        listWeatherDaily = this.weatherDailyWeather.map {
            return@map DomainWeatherDaily(
                time = sdfDaily.format(Date(it.time.toLong() * 1000)),
                dayTemp = "${it.minTemp}\u00B0 / ${it.maxTemp}\u00B0",
                iconWithUrl = it.iconIdWithUrl
            )
        }
    )
}











