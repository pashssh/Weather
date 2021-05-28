package com.pashssh.weather.ui.viewModels

import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.pashssh.weather.App
import com.pashssh.weather.database.getDatabase
import com.pashssh.weather.repository.WeatherRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class ChangeCityViewModel() : ViewModel() {

    private val viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val database = getDatabase(App().applicationContext())
    private val weatherRepository = WeatherRepository(database)

    val listCities = weatherRepository.getList()

    fun addCityInDatabase(latitude: Double, longitude: Double, name: String, cityId: String) {
        coroutineScope.launch {
            weatherRepository.insertNewCity(
                latitude,
                longitude,
                name,
                cityId
            )
        }
    }

    fun updateCityInDatabase(latitude: Double, longitude: Double, name: String, cityId: String) {
        coroutineScope.launch {
            weatherRepository.updateCity(
                latitude,
                longitude,
                name,
                cityId
            )
        }
    }

    fun deleteCityInDatabase(cityId: String) {
        coroutineScope.launch {
            weatherRepository.deleteCity(
                cityId
            )
        }
    }
}
