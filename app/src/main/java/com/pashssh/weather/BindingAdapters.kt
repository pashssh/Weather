package com.pashssh.weather

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.pashssh.weather.database.DatabaseDaily
import com.pashssh.weather.database.DatabaseHourly
import com.pashssh.weather.domain.DomainDaily
import com.pashssh.weather.domain.DomainHourly
import com.pashssh.weather.network.json.HourlyList
import com.pashssh.weather.weatherDisplay.DailyAdapter
import com.pashssh.weather.weatherDisplay.HourlyAdapter
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.*


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

        val sdf = SimpleDateFormat("dd.MM '/' HH:mm")
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

        val sdf = SimpleDateFormat("dd.MM '/' HH:mm")
        val date = Date(it.time.toLong() * 1000)
        sdf.format(date)
        text = sdf.format(date)
    }
}


@BindingAdapter("tempDaily")
fun TextView.setDailyTemp(item: DatabaseDaily?) {
    item?.let {
        val t: String = "${it.minTemp} / ${it.maxTemp}"
        text = t
    }
}

@BindingAdapter("imageDaily")
fun ImageView.setStatusIcon(item: DatabaseDaily?) {
    item.let {

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