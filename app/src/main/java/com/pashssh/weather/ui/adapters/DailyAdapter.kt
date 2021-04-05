package com.pashssh.weather.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.pashssh.weather.databinding.ItemDailyViewBinding
import com.pashssh.weather.domain.DomainWeatherDaily

class DailyAdapter() : ListAdapter<DomainWeatherDaily, DailyAdapter.DailyViewHolder>(DiffCall) {

    class DailyViewHolder(private val binding: ItemDailyViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(domainWeatherDaily: DomainWeatherDaily) {
            binding.dailyItem = domainWeatherDaily
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyViewHolder {
        return DailyViewHolder(
            ItemDailyViewBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: DailyViewHolder, position: Int) {
        val domainDaily = getItem(position)
        holder.bind(domainDaily)
    }

    companion object DiffCall : DiffUtil.ItemCallback<DomainWeatherDaily>() {
        override fun areItemsTheSame(oldItem: DomainWeatherDaily, newItem: DomainWeatherDaily): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: DomainWeatherDaily, newItem: DomainWeatherDaily): Boolean {
            return oldItem == newItem
        }

    }

}