package com.example.reading.presentation.view.fragment

import android.os.Handler
import android.os.Looper
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.reading.databinding.FragmentSplashBinding
import com.example.reading.presentation.view.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashFragment : BaseFragment<FragmentSplashBinding>() {
    override fun createViewBinding() = FragmentSplashBinding.inflate(layoutInflater)

    override fun initializeView() {
        Handler(Looper.getMainLooper()).postDelayed({
            StartFragment.open(findNavController())
        }, 2000)
    }

    override fun bindView() {
        Glide.with(binding.imgLogo)
            .load("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcROdNCij3qBF3VI97g1KRLu6TtXZuqOR3YflxkcfKv0xotEAOgmMzv20s-aW4-2TCqyaFA&usqp=CAU")
            .into(binding.imgLogo)
    }
}