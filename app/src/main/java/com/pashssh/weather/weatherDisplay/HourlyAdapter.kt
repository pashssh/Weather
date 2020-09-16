package com.pashssh.weather.weatherDisplay

import android.database.DatabaseUtils
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.pashssh.weather.R
import com.pashssh.weather.databinding.HourlyWeatherViewItemBinding
import com.pashssh.weather.domain.DomainHourly


class HourlyAdapter() :
    ListAdapter<DomainHourly, HourlyAdapter.DomainHourlyViewHolder>(DiffCallback) {

    class DomainHourlyViewHolder(private val binding: HourlyWeatherViewItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(domainHourly: DomainHourly) {
            binding.hourlyModel = domainHourly
            binding.executePendingBindings()
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<DomainHourly>() {
        override fun areItemsTheSame(oldItem: DomainHourly, newItem: DomainHourly): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: DomainHourly, newItem: DomainHourly): Boolean {
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
        val domainHourly = getItem(position)
        holder.bind(domainHourly)
    }


}