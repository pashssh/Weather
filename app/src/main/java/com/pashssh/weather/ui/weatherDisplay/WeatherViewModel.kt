package com.pashssh.weather.ui.weatherDisplay

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.facebook.stetho.inspector.domstorage.SharedPreferencesHelper
import com.pashssh.weather.database.DatabaseCurrent
import com.pashssh.weather.database.getDatabase
import com.pashssh.weather.initSharedPref
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
        initSharedPref(app.applicationContext)

//        val sharedPref = app.applicationContext.getSharedPreferences("settings", Context.MODE_PRIVATE)
        refWe(sharedPref.getFloat("lat", 1.0F).toDouble(), sharedPref.getFloat("lon", 1.0F).toDouble(), sharedPref.getString("cityName" , "")!!)
        if (sharedPref.contains("cityName")) {
            sharedPref.getString("cityName", "")?.let { updateCurrent(it) }
        }
//        coroutineScope.launch {
//            weatherRepository.refreshWeather(53.993009, 27.567444, "Minsk")
//        }


//
//        val dataSource = weatherRepository.getCurrentWeather("Minsk")
//        currentWeather.addSource(dataSource) { s ->
//            currentWeather.value = s
//
//        }
    }

    fun updateCurrent(x: String) {
        val result = weatherRepository.getCurrentWeather(x)
        currentWeather.addSource(result) {
            currentWeather.value = it
        }
    }

    fun refWe(lat: Double, lon: Double, city: String) {

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