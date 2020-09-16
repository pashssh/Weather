package com.pashssh.weather.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.pashssh.weather.domain.DomainDaily
import com.pashssh.weather.domain.DomainHourly


@Entity
data class DatabaseCurrent constructor(
    val temperature: Int,
    val maxTemp: Int,
    val minTemp: Int,
    @PrimaryKey
    val location: String,
    val feelsLike: Int,
    val cloudsDescription: String
)

@Entity
data class DatabaseHourly constructor(
    @PrimaryKey
    val time: Int,
    val location: String,
    val temperature: Int,
    val cloudsDescription: String,
)

@Entity
data class DatabaseDaily constructor(
    @PrimaryKey
    val time: Int,
    val location: String,
    val minTemp: Int,
    val maxTemp: Int,
    val statusImage: String
)

fun List<DatabaseHourly>.asDomainHourlyModel(): List<DomainHourly> {
    return map {
        DomainHourly(
            time = it.time,
            location = it.location,
            temperature = it.temperature,
            cloudsDescription = it.cloudsDescription
        )
    }
}

fun List<DatabaseDaily>.asDomainDailyModel(): List<DomainDaily> {
    return map {
        DomainDaily(
            time = it.time,
            minTemp = it.minTemp,
            maxTemp = it.maxTemp,
            statusImage = it.statusImage
        )
    }
}














