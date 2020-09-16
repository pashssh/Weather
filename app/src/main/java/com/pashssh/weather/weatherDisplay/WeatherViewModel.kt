package com.pashssh.weather.weatherDisplay

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.pashssh.weather.database.DatabaseCurrent
import com.pashssh.weather.database.getDatabase
import com.pashssh.weather.domain.DomainHourly
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

//    val currentWeather = weatherRepository.currentWeather

    var currentWeather =  weatherRepository.currentWeather
    var hourlyWeather =  weatherRepository.hourlyWeather

    var dailyWeather =  weatherRepository.dailyWeather



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