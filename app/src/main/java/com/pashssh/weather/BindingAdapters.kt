package com.pashssh.weather

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.pashssh.weather.database.LocationItem
import com.pashssh.weather.domain.DomainWeatherDaily
import com.pashssh.weather.domain.DomainWeatherHourly
import com.pashssh.weather.ui.adapters.ChangeCityAdapter
import com.pashssh.weather.ui.adapters.DailyAdapter
import com.pashssh.weather.ui.adapters.HourlyAdapter


//Hourly Adapters
@BindingAdapter("listHourly")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<DomainWeatherHourly>?) {
    val adapter = recyclerView.adapter as HourlyAdapter
//    val list = data?.chunked(24)
//    adapter.submitList(list?.get(0))
    adapter.submitList(data)
}


//DailyAdapters
@BindingAdapter("listDaily")
fun bindRecyclerDaily(recyclerView: RecyclerView, data: List<DomainWeatherDaily>?) {
    val adapter = recyclerView.adapter as DailyAdapter
    adapter.submitList(data)
}


//Any adapters
@BindingAdapter("setWeatherIcon")
fun ImageView.setWeatherIcon(iconId: String) {
    iconId.let {
        val s = "https://openweathermap.org/img/wn/$it@2x.png"
        Glide
            .with(this.context)
            .load(s)
            .placeholder(R.drawable.empty_icon)
            .into(this)
    }

}





//ChangeCity Adapters
@BindingAdapter("listCities")
fun bindRecyclerChangeCity(recyclerView: RecyclerView, data: List<LocationItem>?) {
    val adapter = recyclerView.adapter as ChangeCityAdapter
    adapter.submitList(data)
}

@BindingAdapter("setCityName")
fun TextView.setDailyTemp(city: String?) {
    city?.let {
        text = it
    }
}
