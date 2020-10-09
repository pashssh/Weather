package com.pashssh.weather.ui.changeCity

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.pashssh.weather.databinding.ChangeCityFragmentBinding

class ChangeCityAdapter() :
    ListAdapter<String, ChangeCityAdapter.ChangeCityViewHolder>(ChangeCityDiffCall) {

    companion object ChangeCityDiffCall : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

    }

    class ChangeCityViewHolder(private var binding: ChangeCityFragmentBinding) :
        RecyclerView.ViewHolder(binding.root) {
            fun bind(city: String) {

            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChangeCityViewHolder {
        return ChangeCityViewHolder(
            ChangeCityFragmentBinding.inflate(LayoutInflater.from(parent.context))
        )
    }

    override fun onBindViewHolder(holder: ChangeCityViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

}