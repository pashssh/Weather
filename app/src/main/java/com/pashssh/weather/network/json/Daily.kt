package com.pashssh.weather.network.json

data class Daily(
    val dt: Int,
    val temp: Temp,
    val weather: List<WeatherDaily>,

)