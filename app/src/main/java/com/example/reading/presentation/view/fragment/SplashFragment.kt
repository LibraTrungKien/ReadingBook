package com.example.reading.presentation.view.fragment

import android.os.Handler
import android.os.Looper
import androidx.navigation.fragment.findNavController
import com.example.reading.databinding.FragmentSplashBinding
import com.example.reading.presentation.view.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashFragment : BaseFragment<FragmentSplashBinding>() {
    override fun createViewBinding() = FragmentSplashBinding.inflate(layoutInflater)

    override fun initializeView() {
        Handler(Looper.getMainLooper()).postDelayed({
            MainFragment.open(findNavController())
        }, 2000)
    }
}