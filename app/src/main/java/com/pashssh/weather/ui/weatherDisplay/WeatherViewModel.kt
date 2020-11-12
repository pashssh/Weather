package com.pashssh.weather.ui.weatherDisplay

import android.app.Application
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.pashssh.weather.database.DatabaseWeatherData
import com.pashssh.weather.database.getDatabase
import com.pashssh.weather.getDouble
import com.pashssh.weather.initSharedPref
import com.pashssh.weather.repository.WeatherRepository
import com.pashssh.weather.sharedPreferences
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


    var dataWeather = MediatorLiveData<DatabaseWeatherData>()

    val locList = weatherRepository.getLocations()

    init {
        initSharedPref(app.applicationContext)

        if (sharedPreferences.contains("cityName")
            && sharedPreferences.contains("lat")
            && sharedPreferences.contains("lon")
        ) {
            sharedPreferences.getString("cityName", "")?.let { updateDataWeather(it) }
            refreshWeatherFromNetwork(
                sharedPreferences.getDouble("lat", 1.0),
                sharedPreferences.getDouble("lon", 1.0),
                sharedPreferences.getString("cityName", "")!!
            )
        }
    }

    fun updateDataWeather(x: String) {
        val result = weatherRepository.getWeatherData(x)
        dataWeather.addSource(result) {
            dataWeather.value = it
        }
    }

    fun refreshWeatherFromNetwork(lat: Double, lon: Double, city: String) {
        coroutineScope.launch {
            weatherRepository.refreshWeather(lat, lon, city)
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