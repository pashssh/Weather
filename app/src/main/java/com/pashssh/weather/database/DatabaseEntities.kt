package com.pashssh.weather.database

import androidx.room.Entity
import androidx.room.PrimaryKey


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















