package com.pashssh.weather

import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.pashssh.weather.domain.DomainHourly
import com.pashssh.weather.weatherDisplay.HourlyAdapter


@BindingAdapter("listHourly")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<DomainHourly>?) {
    val adapter = recyclerView.adapter as HourlyAdapter
    adapter.submitList(data)
}

//@BindingAdapter("timeHourly")
//fun bindTextHourlyTime(textView: TextView, time:String?) {
//    textView.text = time
//}