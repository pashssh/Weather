package com.pashssh.weather

import com.pashssh.weather.database.LocationItem

interface WeatherClickListener {
    fun onItemSelectClick(item: LocationItem)

    fun onItemDeleteClick(item: LocationItem)
}
