package com.pashssh.weather.repository

import androidx.lifecycle.LiveData
import com.pashssh.weather.database.DatabaseWeatherData
import com.pashssh.weather.database.WeatherDatabase
import com.pashssh.weather.network.Network
import com.pashssh.weather.network.json.asDatabaseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class WeatherRepository(private val weatherDatabase: WeatherDatabase) {

    val x = "Europe/Minsk"

//    val currentWeather = weatherDatabase.weatherDao.getCurrentWeather(x)

    fun getWeatherData(x: String): LiveData<DatabaseWeatherData> {
        val weatherData = weatherDatabase.weatherDao.getWeatherData(x)
        return weatherData
    }

    fun getLocations(): LiveData<List<String>> {
        val locList = weatherDatabase.weatherDao.getLocationList()
        return locList
    }

    suspend fun refreshWeather(lat: Double, lon: Double, city: String) {
        withContext(Dispatchers.IO) {
            val weatherData = Network.weather.getWeatherOneCall(
                lat,
                lon,
                "01cc66d20087c883ac0f3bdfacfdfb48",
                "metric",
                "ru",
                "minutely"
            ).await()
            weatherDatabase.weatherDao.insertCurrent(weatherData.asDatabaseModel(city))
        }
    }
}


//
//                 53.893009
//                 27.567444