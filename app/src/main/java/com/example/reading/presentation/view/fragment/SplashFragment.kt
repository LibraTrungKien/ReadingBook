package com.example.reading.presentation.view.fragment

import android.os.Handler
import android.os.Looper
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.reading.databinding.FragmentSplashBinding
import com.example.reading.presentation.view.base.BaseFragment
import com.example.reading.presentation.viewmodel.SplashViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashFragment : BaseFragment<FragmentSplashBinding>() {
    private val viewModel: SplashViewModel by viewModels()
    override fun createViewBinding() = FragmentSplashBinding.inflate(layoutInflater)

    override fun initializeView() {
        Handler(Looper.getMainLooper()).postDelayed({
            MainFragment.open(findNavController())
        }, 2000)
    }

    override fun initializeComponent() {
//        apiCall(viewModel.getUser(), viewLifecycleOwner, {
//            Log.d("SplashFragment", "initializeComponent()...$it")
//        }, { false })
    }
}