package com.pashssh.weather.ui.viewModels

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.pashssh.weather.App
import com.pashssh.weather.database.DatabaseWeatherData
import com.pashssh.weather.database.LocationItem
import com.pashssh.weather.database.getDatabase
import com.pashssh.weather.database.loc
import com.pashssh.weather.domain.DomainWeatherData
import com.pashssh.weather.repository.WeatherRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException


class WeatherViewModel(application: Application, val location: LocationItem) : AndroidViewModel(application) {

//    private val _currentWeather = MutableLiveData<DatabaseCurrent>()
//    val currentWeather: LiveData<DatabaseCurrent>
//        get() = _currentWeather

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)
    private val database = getDatabase(App().applicationContext())
    private val weatherRepository = WeatherRepository(database)

    var isEmptyCities = MutableLiveData<Boolean>()


    var dataWeather = MediatorLiveData<DatabaseWeatherData>()

    var data: LiveData<DomainWeatherData>

    val locList = weatherRepository.getCitiesList()


    init {
//        isEmptyCities.value = locList.value.isNullOrEmpty()
//        getWeatherData()
        data = weatherRepository.getWeather(location.cityName)
        Log.i("MYAPP", "test $location")
    }

    fun getWeatherData() {
        refreshWeatherFromNetwork(location.latitude, location.longitude, location.cityName)
//        data = weatherRepository.getWeather(location.cityName)
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

class WeatherViewModelFactory(val app: Application, val location: LocationItem): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WeatherViewModel::class.java)) {
            return WeatherViewModel(app, location) as T
        }
        throw IllegalArgumentException("Unknown View Model class")
    }

}