package com.pashssh.weather.ui

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.facebook.stetho.Stetho
import com.pashssh.weather.App
import com.pashssh.weather.R
import com.pashssh.weather.database.getDatabase
import com.pashssh.weather.repository.WeatherRepository

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        setSupportActionBar(findViewById(R.id.my_toolbar))

        Stetho.initializeWithDefaults(this)

//        val database = getDatabase(App().applicationContext())
//        val weatherRepository = WeatherRepository(database)
//        if(weatherRepository.getLocations().value == null) {
//            val navController = this.findNavController(R.id.nav_host_fragment)
//            navController.navigate(R.id.changeCityFragment)
//        }


//        NavigationUI.setupActionBarWithNavController(this, navController)
    }

//    override fun onSupportNavigateUp(): Boolean {
//        val navController = this.findNavController(R.id.nav_host_fragment)
//        return navController.navigateUp()
//    }
}