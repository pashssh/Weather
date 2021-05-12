package com.pashssh.weather.ui.fragments

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
import com.pashssh.weather.*
import com.pashssh.weather.database.LocationItem
import com.pashssh.weather.databinding.FragmentChangeCityBinding
import com.pashssh.weather.ui.adapters.ChangeCityAdapter
import com.pashssh.weather.ui.viewModels.ChangeCityViewModel


class ChangeCityFragment : Fragment(), WeatherClickListener {

    private val viewModel: ChangeCityViewModel by lazy {
        ViewModelProvider(this).get(
            ChangeCityViewModel::class.java
        )
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentChangeCityBinding.inflate(inflater)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        binding.changeCityRecyclerView.adapter = ChangeCityAdapter(this)

        val autocompleteFragment =
            childFragmentManager.findFragmentById(R.id.autocomplete_fragment)
                    as AutocompleteSupportFragment

        autocompleteFragment.setPlaceFields(
            listOf(
                Place.Field.LAT_LNG,
                Place.Field.NAME,
                Place.Field.ID
            )
        )

        autocompleteFragment.setOnPlaceSelectedListener(object : PlaceSelectionListener {
            override fun onPlaceSelected(place: Place) {
                Toast.makeText(
                    context,
                    "Place: ${place.name}, ${place.latLng}, ${place.id}",
                    Toast.LENGTH_SHORT
                )
                    .show()
                try {
                    viewModel.addCityInDatabase(
                        place.latLng!!.latitude,
                        place.latLng!!.longitude,
                        place.name!!,
                        place.id!!
                    )
                    this@ChangeCityFragment.findNavController()
                        .navigate(
                            ChangeCityFragmentDirections.actionChangeCityFragmentToCitiesViewPager(
                                true
                            )
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


//    private fun navigateOnWeatherFragment(city: String) {
//        this.findNavController()
//            .navigate(ChangeCityFragmentDirections.actionChangeCityFragmentToWeatherFragment(city))
//    }


    override fun onItemSelectClick(item: LocationItem) {
    }

    override fun onItemDeleteClick(item: LocationItem) {
    }


}