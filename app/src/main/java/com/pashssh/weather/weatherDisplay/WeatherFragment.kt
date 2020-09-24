package com.pashssh.weather.weatherDisplay

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.AutocompleteActivity
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import com.pashssh.weather.databinding.WeatherFragmentBinding
import kotlinx.android.synthetic.main.weather_fragment.*

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
        val binding = WeatherFragmentBinding.inflate(inflater)
        binding.lifecycleOwner = this
//        binding.viewModel = ViewModelProvider(this).get(WeatherViewModel::class.java)
        binding.viewModel = viewModel
        binding.hourlyWeatherView.adapter = HourlyAdapter()
        binding.dailyWeatherView.adapter = DailyAdapter()

        if (!Places.isInitialized()) {
            Places.initialize(this.requireContext(), "AIzaSyCs_3xUDy1n-m6UKp47vvpKflxtWqpHVY4")
        }

        binding.button.setOnClickListener {

            viewModel.updateCurrent("America/Toronto")
//
//            val fields = listOf(Place.Field.LAT_LNG)
//            // Start the autocomplete intent.
//            val intent = Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, fields)
//                .build(this.requireContext())
//            startActivityForResult(intent, 1)


        }

        return binding.root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1) {
            when (resultCode) {
                Activity.RESULT_OK -> {
                    data?.let {
                        val place = Autocomplete.getPlaceFromIntent(data)
                        viewModel.refWe(place.latLng!!.latitude, place.latLng!!.longitude)
                        Toast.makeText(this.requireContext(), "${place.latLng!!.latitude}, ${place.latLng!!.longitude}", Toast.LENGTH_SHORT).show()
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