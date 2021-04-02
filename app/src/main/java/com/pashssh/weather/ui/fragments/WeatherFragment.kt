package com.pashssh.weather.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController

import com.pashssh.weather.R
import com.pashssh.weather.database.LocationItem

import com.pashssh.weather.databinding.WeatherFragmentBinding
import com.pashssh.weather.ui.viewModels.WeatherViewModel
import com.pashssh.weather.ui.adapters.DailyAdapter
import com.pashssh.weather.ui.adapters.HourlyAdapter
import com.pashssh.weather.ui.viewModels.WeatherViewModelFactory

class WeatherFragment() : Fragment() {


    lateinit var viewModel: WeatherViewModel
//    private val viewModel: WeatherViewModel by lazy {
//        ViewModelProvider(this, WeatherViewModelFactory(activity?.application, loc)).get(WeatherViewModel::class.java)
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding = WeatherFragmentBinding.inflate(inflater)
        binding.lifecycleOwner = this
        val application = requireNotNull(this.activity).application

        val viewModelFactory = WeatherViewModelFactory(application, arguments?.getSerializable("key") as LocationItem)
        viewModel = ViewModelProvider(this, viewModelFactory).get(WeatherViewModel::class.java)
        binding.viewModel = viewModel
        binding.hourlyWeatherView.adapter = HourlyAdapter()
        binding.dailyWeatherView.adapter = DailyAdapter()


        val toolbar = binding.toolbarLayout
        toolbar.title = " "

        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)


//        viewModel.getWeatherData()

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

    companion object {
        fun newInstance(locationItem: LocationItem) = WeatherFragment().apply {
            arguments = bundleOf(
                "key" to locationItem
            )
        }
    }

}