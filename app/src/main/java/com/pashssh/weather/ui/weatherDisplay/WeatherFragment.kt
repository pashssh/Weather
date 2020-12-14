package com.pashssh.weather.ui.weatherDisplay

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.AutocompleteActivity

import com.pashssh.weather.R

import com.pashssh.weather.databinding.WeatherFragmentBinding

class WeatherFragment : Fragment() {


    private val viewModel: WeatherViewModel by lazy {
        ViewModelProvider(this).get(WeatherViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = WeatherFragmentBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.hourlyWeatherView.adapter = HourlyAdapter()
        binding.dailyWeatherView.adapter = DailyAdapter()

        val toolbar = binding.toolbarLayout
        toolbar.title = " "

        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)

        viewModel.dataWeather.observe(viewLifecycleOwner, Observer {
            if (it == null) {
                Log.e("REPO", "данные по запросу не получены")
            } else {
                Log.e("REPO", "получены данные по запросу")
                toolbar.title = it.location
            }

        })

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.weather_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.changeCity -> this.findNavController().navigate(R.id.changeCityFragment)
        }
        return true
    }

}