package com.pashssh.weather

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.pashssh.weather.database.DatabaseDaily
import com.pashssh.weather.database.DatabaseHourly
import com.pashssh.weather.ui.weatherDisplay.DailyAdapter
import com.pashssh.weather.ui.weatherDisplay.HourlyAdapter
import java.text.SimpleDateFormat
import java.util.*



//Currently Adapters
@BindingAdapter("lastUpdateTime")
fun TextView.setUpdateTime(time: Int) {
    time.let {
        val sdf = SimpleDateFormat("HH:mm")
        val date = Date(it.toLong() * 1000)
        sdf.format(date)
        text = "Обновлено: ${sdf.format(date)}"
    }
}




//Hourly Adapters
@BindingAdapter("listHourly")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<DatabaseHourly>?) {
    val adapter = recyclerView.adapter as HourlyAdapter
//    val list = data?.chunked(24)
//    adapter.submitList(list?.get(0))
    adapter.submitList(data)
}

@BindingAdapter("dateHourlyString")
fun TextView.setHourlyDateString(item: DatabaseHourly?) {
    item?.let {
        val sdf = SimpleDateFormat("HH:mm")
        val date = Date(it.time.toLong() * 1000)
        sdf.format(date)
        text = sdf.format(date)
    }
}

@BindingAdapter("dailyText")
fun TextView.setDailyText(item: DatabaseHourly?) {
    item?.let {
        text = it.temperature.toString()
    }
}







//DailyAdapters
@BindingAdapter("listDaily")
fun bindRecyclerDaily(recyclerView: RecyclerView, data: List<DatabaseDaily>?) {
    val adapter = recyclerView.adapter as DailyAdapter
    adapter.submitList(data)
}

@BindingAdapter("dateDailyString")
fun TextView.setDailyDate(item: DatabaseDaily?) {
    item?.let {
        val sdf = SimpleDateFormat("E, dd MMM")
        val date = Date(it.time.toLong() * 1000)
        sdf.format(date)
        text = sdf.format(date)
    }
}

@BindingAdapter("tempDaily")
fun TextView.setDailyTemp(item: DatabaseDaily?) {
    item?.let {
        val t: String = "${it.minTemp}\u00B0 / ${it.maxTemp}\u00B0"
        text = t
    }
}










//Any adapters
@BindingAdapter("setWeatherIcon")
fun ImageView.setStatusIcon(iconId: String) {
    iconId.let {
        val s = "https://openweathermap.org/img/wn/$it@2x.png"
        Glide
            .with(this.context)
            .load(s)
            .placeholder(R.drawable.ic_baseline_accessibility_new_24)
            .into(this)
    }

}

//@BindingAdapter("timeHourly")
//fun bindTextHourlyTime(textView: TextView, time:String?) {
//    textView.text = time
//}

//@BindingAdapter("sleepQualityString")
//fun TextView.setSleepQualityString(item: SleepNight?) {
//    item?.let {
//        text = convertNumericQualityToString(item.sleepQuality, context.resources)
//    }
//}


//private fun getDateTime(s: String): String? {
//    try {
//        val sdf = SimpleDateFormat("MM/dd/yyyy")
//        val netDate = Date(Long.parseLong(s))
//        return sdf.format(netDate)
//    } catch (e: Exception) {
//        return e.toString()
//    }
//}