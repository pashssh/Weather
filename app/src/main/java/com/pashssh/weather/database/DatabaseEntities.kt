package com.pashssh.weather.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.pashssh.weather.domain.DomainWeatherData
import com.pashssh.weather.domain.DomainWeatherDaily
import com.pashssh.weather.domain.DomainWeatherHourly
import com.pashssh.weather.network.json.Weather
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
    val sunrise: Int,
    val sunset: Int,
    val windSpeed: Double,
    val windDeg: Int,
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
        uvi = this.current.uvi,
        humidity = this.current.humidity,
        sunrise = this.current.sunrise,
        sunset = this.current.sunset,
        windSpeed = this.current.windSpeed,
        windDeg = this.current.windDeg,
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













