package com.pashssh.weather.ui.weatherDisplay

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.pashssh.weather.database.DatabaseWeatherHourly
import com.pashssh.weather.databinding.HourlyWeatherViewItemBinding


class HourlyAdapter() :
    ListAdapter<DatabaseWeatherHourly, HourlyAdapter.DomainHourlyViewHolder>(DiffCallback) {

    class DomainHourlyViewHolder(private val binding: HourlyWeatherViewItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(databaseWeatherHourly: DatabaseWeatherHourly) {
            binding.hourlyModel = databaseWeatherHourly
            binding.executePendingBindings()
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<DatabaseWeatherHourly>() {
        override fun areItemsTheSame(oldItem: DatabaseWeatherHourly, newItem: DatabaseWeatherHourly): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: DatabaseWeatherHourly, newItem: DatabaseWeatherHourly): Boolean {
            return oldItem.time == newItem.time
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DomainHourlyViewHolder {
        return DomainHourlyViewHolder(
            HourlyWeatherViewItemBinding.inflate(
                LayoutInflater.from(
                    parent.context
                )
            )
        )
    }

    override fun onBindViewHolder(holder: DomainHourlyViewHolder, position: Int) {
            val databaseHourly = getItem(position)
            holder.bind(databaseHourly)
    }
}