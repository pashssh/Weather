package com.pashssh.weather

import android.content.Context
import android.content.SharedPreferences
import java.lang.Double.doubleToRawLongBits
import java.lang.Double.longBitsToDouble


const val SETTING = "settings"

lateinit var sharedPreferences: SharedPreferences


fun initSharedPref(context: Context) {
    sharedPreferences = context.getSharedPreferences(SETTING, Context.MODE_PRIVATE)
}


fun SharedPreferences.Editor.putDouble(key: String, double: Double) =
    putLong(key, doubleToRawLongBits(double))

fun SharedPreferences.getDouble(key: String, default: Double) =
    longBitsToDouble(getLong(key, doubleToRawLongBits(default)))