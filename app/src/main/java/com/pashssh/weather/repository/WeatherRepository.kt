package com.pashssh.weather.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.pashssh.weather.database.*
import com.pashssh.weather.domain.DomainWeatherData
import com.pashssh.weather.domain.asDomainModel
import com.pashssh.weather.network.Network
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class WeatherRepository(private val weatherDatabase: WeatherDatabase) {

    val x = "Europe/Minsk"

    fun getWeather(x: String): LiveData<DomainWeatherData> {
        val weatherData = weatherDatabase.weatherDao.getWeatherData(x)
        return Transformations.map(weatherData) {
            it?.asDomainModel()
        }
    }

    fun getList(): LiveData<List<LocationItem>> {
        return weatherDatabase.weatherDao.getList()
    }


    suspend fun refreshWeather(lat: Double, lon: Double, city: String, cityId: String) {
        withContext(Dispatchers.IO) {
            val weatherData = Network.weather.getWeatherOneCall(
                lat,
                lon,
                "01cc66d20087c883ac0f3bdfacfdfb48",
                "metric",
                "ru",
                "minutely"
            ).await()
            weatherDatabase.weatherDao.insertCity(weatherData.asDatabaseModel(city, cityId))
        }
    }

    suspend fun insertNewCity(lat: Double, lon: Double, name: String, cityId: String) {
        withContext(Dispatchers.IO) {
            weatherDatabase.weatherDao.insert(
                LocationItem(name, cityId, lat, lon),
                DatabaseWeatherData(
                    0,
                    0,
                    0,
                    0,
                    lat,
                    lon,
                    name,
                    cityId,
                    "null",
                    0,
                    0.0,
                    0,
                    0,
                    0,
                    0.0,
                    0,
                    "null",
                    listOf(DatabaseWeatherHourly(0, 0, 0, "04d")),
                    listOf(DatabaseWeatherDaily(0, 0, 0, 0, "04d"))
                )
            )
        }
    }

    suspend fun updateCity(lat: Double, lon: Double, name: String, cityId: String) {
        withContext(Dispatchers.IO) {
            weatherDatabase.weatherDao.update(
                LocationItem(name, cityId, lat, lon),
                DatabaseWeatherData(
                    0,
                    0,
                    0,
                    0,
                    lat,
                    lon,
                    name,
                    cityId,
                    "null",
                    0,
                    0.0,
                    0,
                    0,
                    0,
                    0.0,
                    0,
                    "null",
                    listOf(DatabaseWeatherHourly(0, 0, 0, "04d")),
                    listOf(DatabaseWeatherDaily(0, 0, 0, 0, "04d"))
                )
            )
        }
    }

    suspend fun deleteCity(cityId: String) {
        withContext(Dispatchers.IO) {
            weatherDatabase.weatherDao.delete(
                LocationItem("", cityId, 0.0, 0.0),
                DatabaseWeatherData(
                    0,
                    0,
                    0,
                    0,
                    0.0,
                    0.0,
                    "",
                    cityId,
                    "null",
                    0,
                    0.0,
                    0,
                    0,
                    0,
                    0.0,
                    0,
                    "null",
                    listOf(DatabaseWeatherHourly(0, 0, 0, "04d")),
                    listOf(DatabaseWeatherDaily(0, 0, 0, 0, "04d"))
                )
            )
        }
    }


}


//
//                 53.893009
//                 27.567444