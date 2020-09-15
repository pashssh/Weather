package com.pashssh.weather.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.*
import com.pashssh.weather.domain.WeatherDomain
import com.pashssh.weather.network.json.WeatherCurrent

const val loc = "Europe/Moscow"

@Dao
interface WeatherDao {
    @Query("select * from databasecurrent where location = :loc")
    fun getCurrentWeather(loc:String): LiveData<DatabaseCurrent>

    @Query("select * from databasehourly where location = :loc")
    fun getHourlyWeather(loc:String): LiveData<List<DatabaseHourly>>

    @Query("select * from databasedaily where location = :loc")
    fun getDailyWeather(loc:String): LiveData<List<DatabaseDaily>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCurrent(databaseCurrent: DatabaseCurrent)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertHourly(vararg databaseHourly: DatabaseHourly)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDaily(vararg databaseDaily: DatabaseDaily)




//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    fun insertHourly(vararg databaseHourly: DatabaseHourly)

}

@Database(entities = [DatabaseCurrent::class, DatabaseHourly::class, DatabaseDaily::class], version = 1)
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