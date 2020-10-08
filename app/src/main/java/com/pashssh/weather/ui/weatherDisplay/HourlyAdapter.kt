package com.pashssh.weather.ui.weatherDisplay

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.pashssh.weather.database.DatabaseHourly
import com.pashssh.weather.databinding.HourlyWeatherViewItemBinding


class HourlyAdapter() :
    ListAdapter<DatabaseHourly, HourlyAdapter.DomainHourlyViewHolder>(DiffCallback) {

    class DomainHourlyViewHolder(private val binding: HourlyWeatherViewItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(databaseHourly: DatabaseHourly) {
            binding.hourlyModel = databaseHourly
            binding.executePendingBindings()
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<DatabaseHourly>() {
        override fun areItemsTheSame(oldItem: DatabaseHourly, newItem: DatabaseHourly): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: DatabaseHourly, newItem: DatabaseHourly): Boolean {
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