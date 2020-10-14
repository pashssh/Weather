package com.pashssh.weather.ui.weatherDisplay

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.AutocompleteActivity
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import com.pashssh.weather.App

import com.pashssh.weather.R

import com.pashssh.weather.databinding.WeatherFragmentBinding

class WeatherFragment : Fragment() {

    private val viewModel: WeatherViewModel by lazy {
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        ViewModelProvider(this, WeatherViewModel.Factory(activity.application))
            .get(WeatherViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        binding.viewModel = ViewModelProvider(this).get(WeatherViewModel::class.java)

        val binding = WeatherFragmentBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.hourlyWeatherView.adapter = HourlyAdapter()
        binding.dailyWeatherView.adapter = DailyAdapter()

//        arguments?.let {
//            viewModel.updateCurrent(WeatherFragmentArgs.fromBundle(it).selectedCity)
//            Toast.makeText(this.context, WeatherFragmentArgs.fromBundle(it).selectedCity, Toast.LENGTH_SHORT).show()
//        }

        val sharedPref = this.requireContext().getSharedPreferences("settings", Context.MODE_PRIVATE)
        if (sharedPref.contains("cityName")) {
            sharedPref.getString("cityName", "")?.let { viewModel.updateCurrent(it) }
        }


        if (!Places.isInitialized()) {
            Places.initialize(this.requireContext(), "AIzaSyCs_3xUDy1n-m6UKp47vvpKflxtWqpHVY4")
        }

        viewModel.currentWeather.observe(viewLifecycleOwner, Observer {
            if (it == null) {
                Log.e("REPO", "не удалось получить данные по запросу")
            } else {
                Log.e("REPO", "получены данные по запросу")
            }

        })

        binding.button.setOnClickListener {
            val fields = listOf(Place.Field.LAT_LNG, Place.Field.NAME)
            // Start the autocomplete intent.
            val intent = Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, fields)
                .build(this.requireContext())
            startActivityForResult(intent, 1)
        }

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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1) {
            when (resultCode) {
                Activity.RESULT_OK -> {
                    data?.let {
                        val place = Autocomplete.getPlaceFromIntent(data)
                        viewModel.refWe(
                            place.latLng!!.latitude, place.latLng!!.longitude,
                            place.name!!
                        )
                        viewModel.updateCurrent(place.name!!)
                        Toast.makeText(
                            this.requireContext(),
                            "${place.latLng!!.latitude}, ${place.latLng!!.longitude}, ${place.name}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
                AutocompleteActivity.RESULT_ERROR -> {
                    // TODO: Handle the error.
                    data?.let {
                        val status = Autocomplete.getStatusFromIntent(data)
                        status.statusMessage?.let { it1 -> Log.i("TSG22", it1) }
                    }
                }
                Activity.RESULT_CANCELED -> {
                    // The user canceled the operation.
                }
            }
            return
        }
    }


}