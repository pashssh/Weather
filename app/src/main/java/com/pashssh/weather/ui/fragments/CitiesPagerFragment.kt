package com.pashssh.weather.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.ViewPager2
import com.pashssh.weather.database.LocationItem
import com.pashssh.weather.databinding.FragmentCitiesViewPagerBinding
import com.pashssh.weather.ui.CitiesViewPagerAdapter
import com.pashssh.weather.ui.viewModels.CitiesPagerViewModel

class CitiesPagerFragment : Fragment() {

    private lateinit var viewPager : ViewPager2

    private val citiesPagerViewModel: CitiesPagerViewModel by lazy {
        ViewModelProvider(this).get(CitiesPagerViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val binding = FragmentCitiesViewPagerBinding.inflate(inflater)
        binding.lifecycleOwner = this
        viewPager = binding.viewPager2

//        var locationItems = emptyList<LocationItem>()
//        binding.viewPager2.adapter = CitiesViewPagerAdapter(locationItems, this)

        citiesPagerViewModel.listCities.observe(viewLifecycleOwner, Observer<List<LocationItem>> { items ->
            if (items.isEmpty()) {
                this.findNavController()
                    .navigate(CitiesPagerFragmentDirections.actionCitiesViewPagerToChangeCityFragment())
            }
            viewPager.adapter = CitiesViewPagerAdapter(items, this)
//            if (CitiesPagerFragmentArgs)
            val args : CitiesPagerFragmentArgs by navArgs()
            if (args.added) {
                viewPager.currentItem = items.size
            }
        })

        return binding.root
    }




}