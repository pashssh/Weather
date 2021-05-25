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
import com.pashssh.weather.databinding.FragmentWeatherBinding
import com.pashssh.weather.ui.adapters.DailyAdapter
import com.pashssh.weather.ui.adapters.HourlyAdapter
import com.pashssh.weather.ui.viewModels.WeatherViewModel
import com.pashssh.weather.ui.viewModels.WeatherViewModelFactory
import kotlinx.android.synthetic.main.fragment_weather.*

class WeatherFragment() : Fragment() {


    lateinit var viewModel: WeatherViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val application = requireNotNull(this.activity).application
        val viewModelFactory =
            WeatherViewModelFactory(application, arguments?.getSerializable("key") as LocationItem)
        viewModel = ViewModelProvider(this, viewModelFactory).get(WeatherViewModel::class.java)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding = FragmentWeatherBinding.inflate(inflater)
        binding.lifecycleOwner = this

        binding.viewModel = viewModel
        binding.hourlyWeatherView.apply {
            adapter = HourlyAdapter()
            itemAnimator = null
        }
        binding.dailyWeatherView.apply {
            adapter = DailyAdapter()
            itemAnimator = null
        }


//        val toolbar = binding.toolbarLayout
//        toolbar.title = " "

        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)

//        viewModel.getWeatherData()

        viewModel.data.observe(viewLifecycleOwner, Observer {
            toolbar.title = it?.location ?: ""
        })

//        viewModel.dataWeather.observe(viewLifecycleOwner, Observer {
//            if (it == null) {
//                Log.e("REPO", "данные по запросу не получены")
//            } else {
//                Log.e("REPO", "получены данные по запросу")
//                toolbar.title = it.location
//            }
//
//        })


        return binding.root
    }

    override fun onResume() {
        super.onResume()
    }



    companion object {
        fun newInstance(locationItem: LocationItem) = WeatherFragment().apply {
            arguments = bundleOf(
                "key" to locationItem
            )
        }
    }




}