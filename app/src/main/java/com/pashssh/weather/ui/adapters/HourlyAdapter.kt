package com.pashssh.weather.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.pashssh.weather.databinding.HourlyWeatherViewItemBinding
import com.pashssh.weather.domain.DomainWeatherHourly


class HourlyAdapter() :
    ListAdapter<DomainWeatherHourly, HourlyAdapter.DomainHourlyViewHolder>(DiffCallback) {

    class DomainHourlyViewHolder(private val binding: HourlyWeatherViewItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(domainWeatherHourly: DomainWeatherHourly) {
            binding.hourlyItem = domainWeatherHourly
            binding.executePendingBindings()
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<DomainWeatherHourly>() {
        override fun areItemsTheSame(oldItem: DomainWeatherHourly, newItem: DomainWeatherHourly): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: DomainWeatherHourly, newItem: DomainWeatherHourly): Boolean {
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