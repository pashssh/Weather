package com.pashssh.weather.ui.changeCity

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.pashssh.weather.databinding.ChangeCityFragmentBinding
import com.pashssh.weather.databinding.ChangeCityItemBinding

class ChangeCityAdapter() :
    ListAdapter<String, ChangeCityAdapter.ChangeCityViewHolder>(ChangeCityDiffCall) {

    class ChangeCityViewHolder(private val binding: ChangeCityItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(city: String) {
            binding.city = city
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChangeCityViewHolder {
        return ChangeCityViewHolder(
            ChangeCityItemBinding.inflate(LayoutInflater.from(parent.context))
        )
    }

    override fun onBindViewHolder(holder: ChangeCityViewHolder, position: Int) {
        val city = getItem(position)
        holder.bind(city)
    }

    companion object ChangeCityDiffCall : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

    }

}