package com.pashssh.weather.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.pashssh.weather.database.DatabaseCurrent
import com.pashssh.weather.database.WeatherDatabase
import com.pashssh.weather.database.asDomainModel
import com.pashssh.weather.network.Network
import com.pashssh.weather.network.json.asCurrentDatabaseModel

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class WeatherRepository(private val weatherDatabase: WeatherDatabase) {

    val x = "Europe/Minsk"
    val xm = MutableLiveData("Europe/Minsk")

//    val currentWeather = weatherDatabase.weatherDao.getCurrentWeather(x)

    fun getCurrentWeather(x: String): LiveData<DatabaseCurrent> {
        return weatherDatabase.weatherDao.getCurrentWeather(x)
    }


//    val hourlyWeather = Transformations.map(weatherDatabase.weatherDao.getHourlyWeather("Europe/Minsk")) {
//        it.asDomainHourlyModel()
//    }
//    val dailyWeather = Transformations.map(weatherDatabase.weatherDao.getDailyWeather("Europe/Minsk")) {
//        it.asDomainDailyModel()

    suspend fun refreshWeather(lat: Double, lon:Double) {
        withContext(Dispatchers.IO) {
            val weatherData = Network.weather.getWeatherOneCall(
                lat,
                lon,
                "01cc66d20087c883ac0f3bdfacfdfb48",
                "metric",
                "ru",
                "minutely"
            ).await()
            weatherDatabase.weatherDao.insertCurrent(weatherData.asCurrentDatabaseModel())
//            weatherDatabase.weatherDao.insertHourly(*weatherData.asHourlyDatabaseModel())
//            weatherDatabase.weatherDao.insertDaily(*weatherData.asDailyDatabaseModel())

        }

    }
}


//
//                 53.893009
//                 27.567444