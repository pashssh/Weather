package com.pashssh.weather.ui.fragments

import android.animation.ValueAnimator
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
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

class WeatherFragment() : Fragment() {

    private lateinit var toolbar: Toolbar

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

        toolbar = binding.toolbar

        binding.viewModel = viewModel
        binding.hourlyWeatherView.apply {
            adapter = HourlyAdapter()
            itemAnimator = null
        }
        binding.dailyWeatherView.apply {
            adapter = DailyAdapter()
            itemAnimator = null
        }


        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)

        viewModel.data.observe(viewLifecycleOwner, Observer {
            toolbar.title = it?.location ?: ""
        })

//        val arc = findViewById<ArcProgress>(R.id.arcView)
//        arc.setTotalProgress(100)
//
//
//
//        arc.setOnClickListener {
//            val animator = ValueAnimator.ofInt(0, 80).apply {
//                duration = 1500
//                start()
//            }
//            animator.addUpdateListener { anim ->
//                val currentProgress = anim.animatedValue as Int
//                arc.setCurrentProgress(currentProgress)
//
//            }
//        }

        return binding.root
    }




    companion object {
        fun newInstance(locationItem: LocationItem) = WeatherFragment().apply {
            arguments = bundleOf(
                "key" to locationItem
            )
        }
    }
}