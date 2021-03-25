package com.pashssh.weather.ui.viewModels

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

    val listCities = weatherRepository.getLocations()

    fun addCityInDb(latitude: Double, longitude: Double, name: String) {
        coroutineScope.launch {
            weatherRepository.insertCity(
                latitude,
                longitude,
                name
            )
        }


    }

}


// Factory ViewModel

// -> Fragment
//    private val viewModel: ChangeCityViewModel by lazy {
//        val activity = requireNotNull(this.activity)
//        ViewModelProvider(this, ChangeCityViewModel.Factory(activity.application)).get(
//            ChangeCityViewModel::class.java
//        )
//    }

// -> ViewModel
//class Factory(val app: Application) : ViewModelProvider.Factory {
//    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
//        if (modelClass.isAssignableFrom(ChangeCityViewModel::class.java)) {
//            return ChangeCityViewModel(app) as T
//        }
//        throw IllegalArgumentException("Unable to construct viewmodel")
//    }
//}