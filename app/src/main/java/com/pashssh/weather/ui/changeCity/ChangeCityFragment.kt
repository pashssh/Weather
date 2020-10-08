package com.pashssh.weather.ui.changeCity

import android.os.Bundle
import android.renderscript.ScriptGroup
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.pashssh.weather.R
import com.pashssh.weather.databinding.FragmentChangeCityBinding


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
        val binding = FragmentChangeCityBinding.inflate(inflater)

        binding.viewModel = viewModel


        return binding.root
    }

}