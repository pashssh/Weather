package com.pashssh.weather.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.pashssh.weather.database.LocationItem
import com.pashssh.weather.databinding.CitiesViewPagerFragmentBinding
import com.pashssh.weather.ui.CitiesViewPagerAdapter
import com.pashssh.weather.ui.viewModels.CitiesPagerViewModel

class CitiesPagerFragment : Fragment() {

    private val citiesPagerViewModel: CitiesPagerViewModel by lazy {
        ViewModelProvider(this).get(CitiesPagerViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val binding = CitiesViewPagerFragmentBinding.inflate(inflater)
        binding.lifecycleOwner = this
//        binding.viewPager2.adapter = CitiesViewPagerAdapter(emptyList(), this)


//        val list: List<LocationItem> = listOf(LocationItem("Минск", 53.9006, 27.559), LocationItem("Брест", 52.0997, 23.7637))
//        binding.viewPager2.adapter = CitiesViewPagerAdapter(list, this)

//        val x = citiesPagerViewModel.listCities.observe(viewLifecycleOwner, Observer {
//            binding.viewPager2.adapter = CitiesViewPagerAdapter(it, this)
//            viewLifecycleOwner.lifecycle.removeObserver()
//        })

        val observer = Observer<List<LocationItem>> { items ->
            if (items.isEmpty()) {
                this.findNavController()
                    .navigate(CitiesPagerFragmentDirections.actionCitiesViewPagerToChangeCityFragment())
            }
            binding.viewPager2.adapter = CitiesViewPagerAdapter(items, this)
        }
        citiesPagerViewModel.listCities.observe(viewLifecycleOwner, observer)



        return binding.root
    }

}