package com.pashssh.weather.weatherDisplay

import android.app.Application
import android.content.LocusId
import androidx.lifecycle.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.pashssh.weather.database.DatabaseCurrent
import com.pashssh.weather.database.asDomainModel
import com.pashssh.weather.database.getDatabase
import com.pashssh.weather.domain.HourlyDomain
import com.pashssh.weather.domain.WeatherDomain
import com.pashssh.weather.network.Network
import com.pashssh.weather.network.json.DailyWeather
import com.pashssh.weather.network.json.HourlyWeather
import com.pashssh.weather.network.json.asDatabaseModel
import com.pashssh.weather.repository.WeatherRepository
import kotlinx.coroutines.*

class WeatherViewModel(app: Application) : ViewModel() {

//    private val _currentWeather = MutableLiveData<DatabaseCurrent>()
//    val currentWeather: LiveData<DatabaseCurrent>
//        get() = _currentWeather

    private var _x = MutableLiveData<DatabaseCurrent>()
    val x: LiveData<DatabaseCurrent>
        get() = _x

    private val _y = MutableLiveData<List<DailyWeather>>()
    val y: LiveData<List<DailyWeather>>
        get() = _y

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val database = getDatabase(app.applicationContext)
    private val weatherRepository = WeatherRepository(database)

//    val currentWeather = weatherRepository.currentWeather

    var currentWeather =  weatherRepository.currentWeather



    init {
        coroutineScope.launch {
            weatherRepository.refreshWeather()

//            val targetClassHourlyWeather = object : TypeToken<List<HourlyWeather>>(){}.type
//            val gsonHourly: List<HourlyWeather> = Gson().fromJson(currentWeather.value?.hourlyWeather, targetClassHourlyWeather)
//            _x.value = gsonHourly
//
//            val targetClassDailyWeather  = object : TypeToken<List<DailyWeather>>(){}.type
//            val gsonDaily: List<DailyWeather> = Gson().fromJson(currentWeather.value?.dailyWeather, targetClassDailyWeather)
//            _y.value = gsonDaily
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