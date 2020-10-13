package com.pashssh.weather.ui.changeCity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.pashssh.weather.databinding.ChangeCityFragmentBinding


class ChangeCityFragment : Fragment() {

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
        binding.changeCityRecyclerView.adapter = ChangeCityAdapter(ChangeCityAdapter.OnClickListener {
            this.findNavController().navigate(ChangeCityFragmentDirections.actionChangeCityFragmentToWeatherFragment(it))
        })



        return binding.root
    }

}