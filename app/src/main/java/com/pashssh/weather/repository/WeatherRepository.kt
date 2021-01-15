package com.pashssh.weather.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.pashssh.weather.database.*
import com.pashssh.weather.domain.DomainWeatherData
import com.pashssh.weather.network.Network
import com.pashssh.weather.network.json.asDatabaseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class WeatherRepository(private val weatherDatabase: WeatherDatabase) {

    val x = "Europe/Minsk"

    fun getWeather(x: String): LiveData<DomainWeatherData> {
        val weatherData = weatherDatabase.weatherDao.getWeatherData(x)
        return Transformations.map(weatherData) {
            it.asDomainModel()
        }
    }

    fun getLocations(): LiveData<List<LocationItem>> {
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
            weatherDatabase.weatherDao.insertCity(weatherData.asDatabaseModel(city))
        }
    }

    suspend fun insertNullData() {
        withContext(Dispatchers.IO) {
            weatherDatabase.weatherDao.insertCity(DatabaseWeatherData(1,1,1,1,1.0,1.0,"Default", "Default",1,"null",
                listOf(DatabaseWeatherHourly(1,1,1,"@2x")), listOf(DatabaseWeatherDaily(1,1,1,1,"@2x"))
            ))
        }
    }
}


//
//                 53.893009
//                 27.567444