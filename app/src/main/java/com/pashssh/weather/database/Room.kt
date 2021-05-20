package com.pashssh.weather.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*

const val loc = "Europe/Moscow"

@Dao
interface WeatherDao {
    @Query("select * from databaseweatherdata where location = :loc")
    fun getWeatherData(loc: String): LiveData<DatabaseWeatherData>

    @Query("select * from locationitem")
    fun getList(): LiveData<List<LocationItem>>

    @Transaction
    fun insert(l: LocationItem, d: DatabaseWeatherData) {
        insertCity(d)
        insertListItem(l)
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCity(databaseWeatherData: DatabaseWeatherData)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertListItem(locationItem: LocationItem)


    @Transaction
    fun update(l: LocationItem, d: DatabaseWeatherData) {
        updateCity(d)
        updateListItem(l)
    }

    @Update
    fun updateCity(databaseWeatherData: DatabaseWeatherData)

    @Update
    fun updateListItem(locationItem: LocationItem)

}

@Database(
    entities = [DatabaseWeatherData::class, LocationItem::class],
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