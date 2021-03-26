package com.pashssh.weather.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.pashssh.weather.database.LocationItem
import com.pashssh.weather.ui.fragments.WeatherFragment

class CitiesViewPagerAdapter(val listCities: List<LocationItem>, fragment: Fragment) :
    FragmentStateAdapter(fragment) {

    override fun getItemCount() = listCities.size

    override fun createFragment(position: Int): Fragment {
        return WeatherFragment(listCities[position])
    }



}