package com.pashssh.weather

import android.content.Context
import android.content.SharedPreferences


const val SETTING = "settings"

lateinit var sharedPreferences: SharedPreferences


fun initSharedPref(context: Context) {
    sharedPreferences = context.getSharedPreferences(SETTING, Context.MODE_PRIVATE)
}