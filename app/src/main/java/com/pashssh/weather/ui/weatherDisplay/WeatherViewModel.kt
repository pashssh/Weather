package com.pashssh.weather.ui.weatherDisplay

import android.app.Application
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.pashssh.weather.database.DatabaseCurrent
import com.pashssh.weather.database.getDatabase
import com.pashssh.weather.repository.WeatherRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class WeatherViewModel(app: Application) : ViewModel() {

//    private val _currentWeather = MutableLiveData<DatabaseCurrent>()
//    val currentWeather: LiveData<DatabaseCurrent>
//        get() = _currentWeather

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val database = getDatabase(app.applicationContext)
    private val weatherRepository = WeatherRepository(database)


    var currentWeather = MediatorLiveData<DatabaseCurrent>()
    val locList = weatherRepository.getLocations()


    init {
        coroutineScope.launch {
            weatherRepository.refreshWeather(53.993009, 27.567444)
        }

        val dataSource = weatherRepository.getCurrentWeather("Europe/Minsk")
        currentWeather.addSource(dataSource) { s ->
            currentWeather.value = s

        }
    }

    fun updateCurrent(x: String) {
        val result = weatherRepository.getCurrentWeather(x)
        currentWeather.addSource(result) {
            currentWeather.value = it
        }
    }

    fun refWe(lat: Double, lon: Double) {
        coroutineScope.launch {
            weatherRepository.refreshWeather(lat, lon)
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }


    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(WeatherViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return WeatherViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}