package com.example.reading.presentation.view.fragment

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.reading.databinding.FragmentSplashBinding
import com.example.reading.presentation.view.base.BaseFragment
import com.example.reading.presentation.viewmodel.SplashViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SplashFragment : BaseFragment<FragmentSplashBinding>() {
    private val viewModel: SplashViewModel by viewModels()
    override fun createViewBinding() = FragmentSplashBinding.inflate(layoutInflater)

    override fun initializeEvents() {
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main) {
            delay(2000L)
            viewModel.dataLiveData.observe(viewLifecycleOwner) { hasAccount ->
                if (hasAccount) {
                    MainFragment.open(findNavController())
                } else {
                    StartFragment.open(findNavController())
                }
            }
        }

    }

    override fun initializeData() {
        viewModel.getInfoAccount()
    }

    override fun bindView() {
        Glide.with(binding.imgLogo)
            .load("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcROdNCij3qBF3VI97g1KRLu6TtXZuqOR3YflxkcfKv0xotEAOgmMzv20s-aW4-2TCqyaFA&usqp=CAU")
            .into(binding.imgLogo)
    }
}