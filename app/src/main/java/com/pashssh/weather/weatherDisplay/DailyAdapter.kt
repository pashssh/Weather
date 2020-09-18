package com.pashssh.weather.weatherDisplay

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.pashssh.weather.databinding.DailyViewItemBinding
import com.pashssh.weather.domain.DomainDaily

class DailyAdapter() : ListAdapter<DomainDaily, DailyAdapter.DailyViewHolder>(DiffCall) {

    class DailyViewHolder(private val binding: DailyViewItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(domainDaily: DomainDaily) {
            binding.dailyItem = domainDaily
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyViewHolder {
        return DailyViewHolder(
            DailyViewItemBinding.inflate(
                LayoutInflater.from(parent.context)
            )
        )
    }

    override fun onBindViewHolder(holder: DailyViewHolder, position: Int) {
        val domainDaily = getItem(position)
        holder.bind(domainDaily)
    }

    companion object DiffCall : DiffUtil.ItemCallback<DomainDaily>() {
        override fun areItemsTheSame(oldItem: DomainDaily, newItem: DomainDaily): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: DomainDaily, newItem: DomainDaily): Boolean {
            return oldItem == newItem
        }

    }

}