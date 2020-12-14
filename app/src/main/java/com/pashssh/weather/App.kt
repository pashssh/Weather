package com.pashssh.weather

import android.app.Application
import android.content.Context
import android.util.Log
import com.google.android.libraries.places.api.Places

class App : Application() {

    companion object{
        private var instance: Application? = null
        private var context: Context? = null
    }

//    val sharedPref = this.getSharedPreferences("123", Context.MODE_PRIVATE)

    override fun onCreate() {
        super.onCreate()
        instance = this
        context = applicationContext
        initSharedPref(applicationContext())
        if (!Places.isInitialized()) {
            Places.initialize(applicationContext(), "AIzaSyCs_3xUDy1n-m6UKp47vvpKflxtWqpHVY4")
        }
        Log.i("pash", "trtrt")
    }

    fun applicationContext(): Context {
        return context!!
    }

    fun appInstance(): Context {
        return instance!!
    }

}