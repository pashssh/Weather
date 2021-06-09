package com.pashssh.weather.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.pashssh.weather.database.LocationItem
import com.pashssh.weather.databinding.ItemChangeCityBinding

class ChangeCityAdapter(
    private val selectCityListener: SelectCityListener,
    private val deleteCityListener: DeleteCityListener
) :
    ListAdapter<LocationItem, ChangeCityAdapter.ChangeCityViewHolder>(ChangeCityDiffCall) {

    class ChangeCityViewHolder(
        private val binding: ItemChangeCityBinding
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            item: LocationItem,
            selectCityListener: SelectCityListener,
            deleteCityListener: DeleteCityListener
        ) {
            binding.item = item
            binding.selectCityListener = selectCityListener
            binding.deleteCityListener = deleteCityListener
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
        holder.bind(item, selectCityListener, deleteCityListener)
    }

    companion object ChangeCityDiffCall : DiffUtil.ItemCallback<LocationItem>() {
        override fun areItemsTheSame(oldItem: LocationItem, newItem: LocationItem): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: LocationItem, newItem: LocationItem): Boolean {
            return oldItem == newItem
        }
    }
}

class DeleteCityListener(val deleteListener: (cityId: String) -> Unit) {
    fun onClick(cityId: String) = deleteListener(cityId)
}

class SelectCityListener(val selectListener: (item: LocationItem) -> Unit) {
    fun onClick(item: LocationItem) = selectListener(item)
}



