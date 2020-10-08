package com.pashssh.weather.ui.changeCity

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.pashssh.weather.database.WeatherDatabase
import com.pashssh.weather.database.getDatabase
import com.pashssh.weather.repository.WeatherRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

class ChangeCityViewModel(app: Application) : ViewModel() {

    private val viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val database = getDatabase(app.applicationContext)
    private val repository = WeatherRepository(database)

    val listCities = repository.getLocations()


    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ChangeCityViewModel::class.java)) {
                return ChangeCityViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
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