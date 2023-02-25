package com.example.reading.presentation.view.fragment

import androidx.navigation.NavController
import com.example.reading.R
import com.example.reading.databinding.FragmentSearchBinding
import com.example.reading.presentation.view.base.BaseFragment

class SearchFragment : BaseFragment<FragmentSearchBinding>() {
    override fun createViewBinding() = FragmentSearchBinding.inflate(layoutInflater)

    companion object {
        fun open(navController: NavController) {
            navController.navigate(R.id.actSearchFragment)
        }
    }

    override fun initializeView() {
        binding.webView.loadUrl("https://google.com")
    }
}