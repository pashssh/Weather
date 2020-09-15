package com.pashssh.weather.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.pashssh.weather.database.DatabaseCurrent
import com.pashssh.weather.database.WeatherDatabase
import com.pashssh.weather.database.asDomainModel
import com.pashssh.weather.database.loc
import com.pashssh.weather.domain.WeatherDomain
import com.pashssh.weather.network.Network
import com.pashssh.weather.network.json.HourlyWeather
import com.pashssh.weather.network.json.asDatabaseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class WeatherRepository(private val weatherDatabase: WeatherDatabase) {



    val currentWeather = weatherDatabase.weatherDao.getCurrentWeather("Asia/Tehran")


    suspend fun refreshWeather() {
        withContext(Dispatchers.IO) {
            val weatherData = Network.weather.getWeatherOneCall(
                35.21,
                59.1,
                "01cc66d20087c883ac0f3bdfacfdfb48",
                "metric",
                "ru",
                "minutely"
            ).await()
            weatherDatabase.weatherDao.insertCurrent(weatherData.asDatabaseModel())
//            weatherDatabase.weatherDao.insertHourly(*weatherData.asDatabaseHourly())

        }

    }
}


//
//53.893009,
//27.567444,