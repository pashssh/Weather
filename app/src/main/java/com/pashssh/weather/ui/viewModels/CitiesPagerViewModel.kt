package com.pashssh.weather.ui.viewModels

import androidx.lifecycle.ViewModel
import com.pashssh.weather.App
import com.pashssh.weather.database.getDatabase
import com.pashssh.weather.repository.WeatherRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import java.util.concurrent.Executor

class CitiesPagerViewModel() : ViewModel() {

    private val viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val database = getDatabase(App().applicationContext())
    private val weatherRepository = WeatherRepository(database)

    val listCities = weatherRepository.getCitiesList()

    fun getList() {

    }

}