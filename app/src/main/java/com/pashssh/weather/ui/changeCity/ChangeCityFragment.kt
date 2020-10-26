package com.pashssh.weather.ui.changeCity

import android.content.Context
import android.os.Bundle
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
                Toast.makeText(context, "Place: ${place.name}, ${place.id}", Toast.LENGTH_SHORT)
                    .show()
                navigateOnWeatherFragment(
                    place.name!!,
                    place.latLng!!.latitude,
                    place.latLng!!.longitude
                )
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
        this.findNavController()
            .navigate(ChangeCityFragmentDirections.actionChangeCityFragmentToWeatherFragment(city))
        val sharedPref =
            this.requireContext().getSharedPreferences("settings", Context.MODE_PRIVATE)
        sharedPref.edit()
            .putString("cityName", city)
            .putFloat("lat", lat.toFloat())
            .putFloat("lon", lon.toFloat())
            .apply()
    }

    override fun onItemDeleteClick(city: String) {
    }

}