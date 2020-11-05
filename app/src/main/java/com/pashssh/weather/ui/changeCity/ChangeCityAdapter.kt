package com.pashssh.weather.ui.changeCity

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.pashssh.weather.WeatherClickListener
import com.pashssh.weather.database.LocationItem
import com.pashssh.weather.databinding.ChangeCityItemBinding

//class ChangeCityAdapter(val onClickListener: OnClickListener) :
class ChangeCityAdapter(private val weatherClickListener: WeatherClickListener) :
    ListAdapter<LocationItem, ChangeCityAdapter.ChangeCityViewHolder>(ChangeCityDiffCall) {

    class ChangeCityViewHolder(private val binding: ChangeCityItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: LocationItem) {
            binding.item = item
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChangeCityViewHolder {
        return ChangeCityViewHolder(
            ChangeCityItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ChangeCityViewHolder, position: Int) {
        val item = getItem(position)
//        holder.itemView.setOnClickListener {
//            onClickListener.clickListener(city)
//        }
        holder.itemView.setOnClickListener {
            weatherClickListener.onItemSelectClick(item)
        }
        holder.bind(item)
    }

    companion object ChangeCityDiffCall : DiffUtil.ItemCallback<LocationItem>() {
        override fun areItemsTheSame(oldItem: LocationItem, newItem: LocationItem): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: LocationItem, newItem: LocationItem): Boolean {
            return oldItem == newItem
        }

    }

//    class OnClickListener(val clickListener: (city: String) -> Unit) {
//        fun onClick(city: String) {
//            clickListener(city)
//        }
//    }
}