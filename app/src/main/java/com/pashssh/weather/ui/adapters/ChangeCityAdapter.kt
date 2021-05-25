package com.pashssh.weather.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.pashssh.weather.App
import com.pashssh.weather.WeatherClickListener
import com.pashssh.weather.database.LocationItem
import com.pashssh.weather.databinding.ItemChangeCityBinding
import kotlinx.android.synthetic.main.item_change_city.view.*

//class ChangeCityAdapter(val onClickListener: OnClickListener) :
class ChangeCityAdapter(private val weatherClickListener: WeatherClickListener) :
    ListAdapter<LocationItem, ChangeCityAdapter.ChangeCityViewHolder>(ChangeCityDiffCall) {

    class ChangeCityViewHolder(private val binding: ItemChangeCityBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: LocationItem) {
            binding.item = item
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChangeCityViewHolder {
        return ChangeCityViewHolder(
            ItemChangeCityBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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
        holder.itemView.change_city_btn_delete.setOnClickListener {
            weatherClickListener.onItemDeleteClick(item)
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