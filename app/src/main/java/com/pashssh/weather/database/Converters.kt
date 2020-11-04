package com.pashssh.weather.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class HourlyConverter {

    @TypeConverter
    fun fromListHourly(weathers: List<DatabaseWeatherHourly>): String? {
        val gson = Gson()
        val json: String = gson.toJson(weathers)
        return json
    }

    @TypeConverter
    fun fromHourly(weathersString: String): List<DatabaseWeatherHourly> {
        val listType = object : TypeToken<List<DatabaseWeatherHourly>>() {}.type
        val xes: List<DatabaseWeatherHourly> = Gson().fromJson<List<DatabaseWeatherHourly>>(weathersString, listType)
        return xes
    }
}

class DailyConverter {

    @TypeConverter
    fun fromListDaily(weathers: List<DatabaseWeatherDaily>): String? {
        val gson = Gson()
        val json: String = gson.toJson(weathers)
        return json
    }

    @TypeConverter
    fun fromDaily(weathersString: String): List<DatabaseWeatherDaily> {
        val list = object : TypeToken<List<DatabaseWeatherDaily>>() {}.type
        val xes: List<DatabaseWeatherDaily> = Gson().fromJson<List<DatabaseWeatherDaily>>(weathersString, list)
        return xes
    }
}