package com.pashssh.weather.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.pashssh.weather.domain.WeatherDomain
import com.pashssh.weather.network.json.DailyWeather
import com.pashssh.weather.network.json.HourlyWeather
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlin.math.min


@Entity
data class DatabaseCurrent constructor(
    val temperature: Int,
    val maxTemp: Int,
    val minTemp: Int,
    @PrimaryKey
    val location: String,
    val feelsLike: Int,
    val cloudsDescription: String,
    val hourlyWeather: String,
    val dailyWeather: String
//    val hourlyList: List<DatabaseHourly>
)

fun DatabaseCurrent.asDomainModel(): WeatherDomain {
    val targetClassHourlyWeather = object : TypeToken<List<HourlyWeather>>(){}.type
    val targetClassDailyWeather  = object : TypeToken<List<DailyWeather>>(){}.type
    return WeatherDomain(
        temperature = this.temperature,
        maxTemp = this.maxTemp,
        minTemp = this.minTemp,
        location = this.location,
        feelsLike = this.feelsLike,
        cloudsDescription = this.cloudsDescription,
        hourlyWeather = Gson().fromJson(this.hourlyWeather, targetClassHourlyWeather),
        dailyWeather = Gson().fromJson(this.dailyWeather, targetClassDailyWeather)
    )
}













