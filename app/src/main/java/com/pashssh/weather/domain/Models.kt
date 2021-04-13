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
    val uvi: String,
    val humidity: String,
    val sunrise: String,
    val sunset: String,
    val windSpeed: String,
    val windDeg: String,
    val listWeatherHourly: List<DomainWeatherHourly>,
    val listWeatherDaily: List<DomainWeatherDaily>
)

data class DomainWeatherHourly(
    val time: String,
    val temperature: String,
    val iconWithUrl: String,
)

data class DomainWeatherDaily(
    val time: String,
    val dayTemp: String,
    val iconWithUrl: String
)


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
        sunrise = sdfHourly.format(Date(this.sunrise.toLong() * 1000)),
        sunset = sdfHourly.format(Date(this.sunset.toLong() * 1000)),
        windSpeed = "${this.windSpeed}",
        windDeg = windDegToString(this.windDeg),
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

fun windDegToString(windDeg: Int): String {
    return when (windDeg) {
        in 0..22 -> "Север"
        in 23..67 -> "Северо-Восток"
        in 68..112 -> "Восток"
        in 113..157 -> "Юго-Восток"
        in 158..202 -> "Юг"
        in 203..247 -> "Юго-Запад"
        in 248..292 -> "Запад"
        in 293..337 -> "Северо-Запад"
        in 339..360 -> "Север"
        else -> "Север"
    }
}



