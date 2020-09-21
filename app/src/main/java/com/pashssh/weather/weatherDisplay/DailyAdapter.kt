package com.pashssh.weather.weatherDisplay

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.pashssh.weather.database.DatabaseDaily
import com.pashssh.weather.databinding.DailyViewItemBinding
import com.pashssh.weather.domain.DomainDaily

class DailyAdapter() : ListAdapter<DatabaseDaily, DailyAdapter.DailyViewHolder>(DiffCall) {

    class DailyViewHolder(private val binding: DailyViewItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(databaseDaily: DatabaseDaily) {
            binding.dailyItem = databaseDaily
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
        val databaseDaily = getItem(position)
        holder.bind(databaseDaily)
    }

    companion object DiffCall : DiffUtil.ItemCallback<DatabaseDaily>() {
        override fun areItemsTheSame(oldItem: DatabaseDaily, newItem: DatabaseDaily): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: DatabaseDaily, newItem: DatabaseDaily): Boolean {
            return oldItem == newItem
        }

    }

}