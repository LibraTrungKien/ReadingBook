package com.example.reading.presentation.view.fragment

import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.reading.R
import com.example.reading.databinding.FragmentStartBinding
import com.example.reading.presentation.view.base.BaseFragment

class StartFragment : BaseFragment<FragmentStartBinding>() {

    companion object {
        fun open(navController: NavController) {
            navController.navigate(R.id.startFragment)
        }
    }

    override fun createViewBinding() = FragmentStartBinding.inflate(layoutInflater)

    override fun initializeEvents() {
        binding.btnReader.setOnClickListener { MainFragment.open(findNavController()) }
        binding.btnAuthor.setOnClickListener { LoginFragment.open(findNavController()) }
    }
}