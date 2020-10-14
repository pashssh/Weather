package com.pashssh.weather

interface WeatherClickListener {
    fun onItemSelectClick(city: String);
    fun onItemDeleteClick(city: String);
}