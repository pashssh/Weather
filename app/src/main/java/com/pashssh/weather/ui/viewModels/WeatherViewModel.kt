package com.pashssh.weather.ui.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pashssh.weather.App
import com.pashssh.weather.database.DatabaseWeatherData
import com.pashssh.weather.database.getDatabase
import com.pashssh.weather.domain.DomainWeatherData
import com.pashssh.weather.repository.WeatherRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


class WeatherViewModel(val location: String) : ViewModel() {

//    private val _currentWeather = MutableLiveData<DatabaseCurrent>()
//    val currentWeather: LiveData<DatabaseCurrent>
//        get() = _currentWeather

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)
    private val database = getDatabase(App().applicationContext())
    private val weatherRepository = WeatherRepository(database)

     var isEmptyCities = MutableLiveData<Boolean>()


    var dataWeather = MediatorLiveData<DatabaseWeatherData>()

    lateinit var data: LiveData<DomainWeatherData>

    val locList = weatherRepository.getLocations()

    init {
        isEmptyCities.value = locList.value.isNullOrEmpty()
    }

    fun getWeatherData() {
        data = weatherRepository.getWeather(location)
    }

//    init {
//
//        if (sharedPreferences.contains("cityName")
//            && sharedPreferences.contains("lat")
//            && sharedPreferences.contains("lon")
//        ) {
//            sharedPreferences.getString("cityName", "")?.let { updateDataWeather(it) }
//            refreshWeatherFromNetwork(
//                sharedPreferences.getDouble("lat", 1.0),
//                sharedPreferences.getDouble("lon", 1.0),
//                sharedPreferences.getString("cityName", "")!!
//            )
//        }
//    }

//    fun updateDataWeather(x: String) {
//        val result = weatherRepository.getWeatherData(x)
//        dataWeather.addSource(result) {
//            dataWeather.value = it
//        }
//    }

    fun refreshWeatherFromNetwork(lat: Double, lon: Double, city: String) {
        val launch = coroutineScope.launch {
            weatherRepository.refreshWeather(lat, lon, city)
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }


}