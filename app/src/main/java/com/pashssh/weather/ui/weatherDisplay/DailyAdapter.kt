package com.pashssh.weather.ui.weatherDisplay

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.pashssh.weather.database.DatabaseWeatherDaily
import com.pashssh.weather.databinding.DailyViewItemBinding

class DailyAdapter() : ListAdapter<DatabaseWeatherDaily, DailyAdapter.DailyViewHolder>(DiffCall) {

    class DailyViewHolder(private val binding: DailyViewItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(databaseWeatherDaily: DatabaseWeatherDaily) {
            binding.dailyItem = databaseWeatherDaily
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyViewHolder {
        return DailyViewHolder(
            DailyViewItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: DailyViewHolder, position: Int) {
        val databaseDaily = getItem(position)
        holder.bind(databaseDaily)
    }

    companion object DiffCall : DiffUtil.ItemCallback<DatabaseWeatherDaily>() {
        override fun areItemsTheSame(oldItem: DatabaseWeatherDaily, newItem: DatabaseWeatherDaily): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: DatabaseWeatherDaily, newItem: DatabaseWeatherDaily): Boolean {
            return oldItem == newItem
        }

    }

}