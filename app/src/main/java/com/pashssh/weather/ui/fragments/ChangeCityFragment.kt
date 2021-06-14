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
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.common.api.Status
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import com.pashssh.weather.*
import com.pashssh.weather.database.LocationItem
import com.pashssh.weather.databinding.FragmentChangeCityBinding
import com.pashssh.weather.ui.adapters.ChangeCityAdapter
import com.pashssh.weather.ui.adapters.DeleteCityListener
import com.pashssh.weather.ui.adapters.SelectCityListener
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
        val changeCityRecyclerView = binding.changeCityRecyclerView
        changeCityRecyclerView.adapter = ChangeCityAdapter(
            SelectCityListener { item -> viewModel.selectCity(item) },
            DeleteCityListener { cityId -> viewModel.deleteCity(cityId) }
        )

        val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.Callback() {
            override fun getMovementFlags(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder
            ): Int {
                return makeMovementFlags(0, ItemTouchHelper.END)
            }

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                TODO("Not yet implemented")
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                Toast.makeText(
                    this@ChangeCityFragment.context,
                    "${viewModel.listCities.value?.get(viewHolder.absoluteAdapterPosition)}",
                    Toast.LENGTH_SHORT
                ).show()
                viewModel.listCities.value?.let {
                    viewModel.deleteCity(it[viewHolder.absoluteAdapterPosition].cityID)
                }
            }

        }).attachToRecyclerView(changeCityRecyclerView)


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
                    insertOrUpdate(place)
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

    private fun insertOrUpdate(place: Place) {
        if (viewModel.listCities.value?.map { it.cityID }?.contains(place.id!!) != true) {
            viewModel.addCityInDatabase(
                place.latLng!!.latitude,
                place.latLng!!.longitude,
                place.name!!,
                place.id!!
            )
            navigateToViewPager(place, true)
        } else {
            viewModel.updateCityInDatabase(
                place.latLng!!.latitude,
                place.latLng!!.longitude,
                place.name!!,
                place.id!!
            )
            navigateToViewPager(place)
        }
    }

    private fun navigateToViewPager(place: Place, addedCity: Boolean? = false) {
        this@ChangeCityFragment.findNavController()
            .navigate(
                ChangeCityFragmentDirections.actionChangeCityFragmentToCitiesViewPager(
                    addedCity ?: false, place.id!!
                )
            )
    }

    override fun onItemSelectClick(item: LocationItem) {
        Toast.makeText(App().applicationContext(), "select", Toast.LENGTH_SHORT).show()
    }

    override fun onItemDeleteClick(item: LocationItem) {
//        viewModel.deleteCityInDatabase(item.cityID)
//        Log.i("MYTAG", item.cityID)
        Toast.makeText(App().applicationContext(), "delete", Toast.LENGTH_SHORT).show()


    }


}