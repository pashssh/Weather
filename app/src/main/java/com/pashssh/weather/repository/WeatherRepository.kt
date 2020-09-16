package com.pashssh.weather.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.pashssh.weather.database.WeatherDatabase
import com.pashssh.weather.database.asDomainDailyModel
import com.pashssh.weather.database.asDomainHourlyModel
import com.pashssh.weather.domain.DomainHourly
import com.pashssh.weather.network.Network
import com.pashssh.weather.network.json.asCurrentDatabaseModel
import com.pashssh.weather.network.json.asDailyDatabaseModel
import com.pashssh.weather.network.json.asHourlyDatabaseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class WeatherRepository(private val weatherDatabase: WeatherDatabase) {



    val currentWeather = weatherDatabase.weatherDao.getCurrentWeather("Europe/Minsk")
    val hourlyWeather = Transformations.map(weatherDatabase.weatherDao.getHourlyWeather("Europe/Minsk")) {
        it.asDomainHourlyModel()
    }
    val dailyWeather = Transformations.map(weatherDatabase.weatherDao.getDailyWeather("Europe/Minsk")) {
        it.asDomainDailyModel()
    }


    suspend fun refreshWeather() {
        withContext(Dispatchers.IO) {
            val weatherData = Network.weather.getWeatherOneCall(
                53.893009,
                27.567444,
                "01cc66d20087c883ac0f3bdfacfdfb48",
                "metric",
                "ru",
                "minutely"
            ).await()
            weatherDatabase.weatherDao.insertCurrent(weatherData.asCurrentDatabaseModel())
            weatherDatabase.weatherDao.insertHourly(*weatherData.asHourlyDatabaseModel())
            weatherDatabase.weatherDao.insertDaily(*weatherData.asDailyDatabaseModel())
//            weatherDatabase.weatherDao.insertHourly(*weatherData.asDatabaseHourly())

        }

    }
}


//
//53.893009,
//27.567444,