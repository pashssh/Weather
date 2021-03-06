package com.pashssh.weather.ui.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.ViewPager2
import com.pashssh.weather.R
import com.pashssh.weather.database.LocationItem
import com.pashssh.weather.databinding.FragmentCitiesViewPagerBinding
import com.pashssh.weather.ui.adapters.CitiesViewPagerAdapter
import com.pashssh.weather.ui.viewModels.CitiesPagerViewModel

class CitiesPagerFragment : Fragment() {

    private lateinit var viewPager: ViewPager2

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

        citiesPagerViewModel.listCities.observe(
            viewLifecycleOwner,
            Observer<List<LocationItem>> { items ->
                val args: CitiesPagerFragmentArgs by navArgs()
                if (items.isEmpty()) {
                    if (!args.added) {
                        this.findNavController()
                            .navigate(CitiesPagerFragmentDirections.actionCitiesViewPagerToChangeCityFragment())
                    } else {
                        viewPager.adapter = CitiesViewPagerAdapter(emptyList(), this)
                    }
                } else {
                    viewPager.adapter = CitiesViewPagerAdapter(items, this)
//                    val list = items.map { it.cityID }
//                    if (list.contains(args.placeId)) {
//                        viewPager.currentItem = items.indexOf(items[list.indexOf(args.placeId)])
//
//                    }
                }
            })
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.weather_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.changeCity -> this.findNavController()
                .navigate(CitiesPagerFragmentDirections.actionCitiesViewPagerToChangeCityFragment())
        }
        return true
    }

    override fun onStop() {
        super.onStop()
//        viewPager.adapter = CitiesViewPagerAdapter(emptyList(), this)
    }
}