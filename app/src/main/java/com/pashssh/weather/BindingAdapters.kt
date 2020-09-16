package com.pashssh.weather

import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.pashssh.weather.domain.DomainHourly
import com.pashssh.weather.weatherDisplay.HourlyAdapter
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.*


@BindingAdapter("listHourly")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<DomainHourly>?) {
    val adapter = recyclerView.adapter as HourlyAdapter
    adapter.submitList(data)
}

@BindingAdapter("dateHourlyString")
fun TextView.setHourlyDateString(item: DomainHourly?) {
    item?.let {

        val sdf = SimpleDateFormat("dd.MM '/' HH:mm")
        val date = Date(it.time.toLong() * 1000)
        sdf.format(date)
        text = sdf.format(date)

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