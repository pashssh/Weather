package com.pashssh.weather.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.pashssh.weather.network.json.HourlyList


class HourlyConverter {

    @TypeConverter
    fun fromListHourly(weathers: List<DatabaseHourly>): String? {
        val gson = Gson()
        val json: String = gson.toJson(weathers)
        return json
    }

    @TypeConverter
    fun fromHourly(weathersString: String): List<DatabaseHourly> {
        val listType = object : TypeToken<List<DatabaseHourly>>() {}.type
        val x: List<DatabaseHourly> = Gson().fromJson<List<DatabaseHourly>>(weathersString, listType)
        return x
    }
}

class DailyConverter {

    @TypeConverter
    fun fromListDaily(weathers: List<DatabaseDaily>): String? {
        val gson = Gson()
        val json: String = gson.toJson(weathers)
        return json
    }

    @TypeConverter
    fun fromDaily(weathersString: String): List<DatabaseDaily> {
        val list = object : TypeToken<List<DatabaseDaily>>() {}.type
        val x: List<DatabaseDaily> = Gson().fromJson<List<DatabaseDaily>>(weathersString, list)
        return x
    }
}