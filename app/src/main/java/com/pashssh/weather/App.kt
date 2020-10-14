package com.pashssh.weather

import android.app.Application
import android.content.Context

class App : Application() {

    val sharedPref = this.getSharedPreferences("123", Context.MODE_PRIVATE)

}