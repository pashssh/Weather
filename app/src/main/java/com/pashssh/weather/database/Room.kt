package com.pashssh.weather.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*

const val loc = "Europe/Moscow"

@Dao
interface WeatherDao {
    @Query("select * from databaseweatherdata where location = :loc")
    fun getWeatherData(loc: String): LiveData<DatabaseWeatherData>

    @Query("select location, latitude, longitude from databaseweatherdata")
    fun getLocationList(): LiveData<List<LocationItem>>

    @Query("select location, latitude, longitude from databaseweatherdata limit 1")
    fun getLocation(): LocationItem


//
//    @Query("select * from databasedaily where location = :loc")
//    fun getDailyWeather(loc: String): LiveData<List<DatabaseDaily>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCurrent(databaseCurrent: DatabaseWeatherData)

}

@Database(
    entities = [DatabaseWeatherData::class],
    version = 1
)
@TypeConverters(HourlyConverter::class, DailyConverter::class)
abstract class WeatherDatabase : RoomDatabase() {
    abstract val weatherDao: WeatherDao
}

private lateinit var INSTANCE: WeatherDatabase

fun getDatabase(context: Context): WeatherDatabase {
    synchronized(WeatherDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(
                context.applicationContext,
                WeatherDatabase::class.java,
                "weather"
            ).build()
        }
    }
    return INSTANCE
}