package com.pashssh.weather.ui.changeCity

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.gms.common.api.Status
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import com.pashssh.weather.R
import com.pashssh.weather.WeatherClickListener
import com.pashssh.weather.databinding.ChangeCityFragmentBinding
import com.pashssh.weather.putDouble
import com.pashssh.weather.sharedPreferences


class ChangeCityFragment : Fragment(), WeatherClickListener {

    private val viewModel: ChangeCityViewModel by lazy {
        val activity = requireNotNull(this.activity)
        ViewModelProvider(this, ChangeCityViewModel.Factory(activity.application)).get(
            ChangeCityViewModel::class.java
        )
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = ChangeCityFragmentBinding.inflate(inflater)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        binding.changeCityRecyclerView.adapter = ChangeCityAdapter(this)
//        binding.changeCityRecyclerView.adapter = ChangeCityAdapter(ChangeCityAdapter.OnClickListener {
//            this.findNavController().navigate(ChangeCityFragmentDirections.actionChangeCityFragmentToWeatherFragment(it))
//        })

        val autocompleteFragment =
            childFragmentManager.findFragmentById(R.id.autocomplete_fragment)
                    as AutocompleteSupportFragment

        // Specify the types of place data to return.
        autocompleteFragment.setPlaceFields(listOf(Place.Field.LAT_LNG, Place.Field.NAME))

        // Set up a PlaceSelectionListener to handle the response.
        autocompleteFragment.setOnPlaceSelectedListener(object : PlaceSelectionListener {
            override fun onPlaceSelected(place: Place) {
                Toast.makeText(context, "Place: ${place.name}, ${place.latLng}", Toast.LENGTH_SHORT)
                    .show()
                try {
                    navigateOnWeatherFragment(
                        place.name!!,
                        place.latLng!!.latitude,
                        place.latLng!!.longitude
                    )
                } catch (e: Exception) {
                    Log.i("APP_ERR", e.message.toString())
                }
            }

            override fun onError(p0: Status) {
                Toast.makeText(context, p0.statusMessage, Toast.LENGTH_SHORT).show()
            }
        })



        return binding.root
    }

    override fun onItemSelectClick(city: String) {
//        navigateOnWeatherFragment(city)
    }

    private fun navigateOnWeatherFragment(city: String, lat: Double, lon: Double) {

        sharedPreferences.edit()
            .putString("cityName", city)
            .putDouble("lat", lat)
            .putDouble("lon", lon)
            .apply()
        this.findNavController()
            .navigate(ChangeCityFragmentDirections.actionChangeCityFragmentToWeatherFragment(city))
    }

    override fun onItemDeleteClick(city: String) {
    }

}