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
            Places.initialize(applicationContext(), "AIzaSyBlCeTl2bgMWK32cHonl0YVSzWCuSzfdD0")
        }
    }

    fun applicationContext(): Context {
        return context!!
    }

    fun appInstance(): Context {
        return instance!!
    }

}