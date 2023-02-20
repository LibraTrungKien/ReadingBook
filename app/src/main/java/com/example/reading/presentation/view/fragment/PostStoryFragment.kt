package com.example.reading.presentation.view.fragment

import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import com.example.reading.R
import com.example.reading.databinding.FragmentPostStoryBinding
import com.example.reading.presentation.view.base.BaseFragment
import com.example.reading.presentation.viewmodel.PostStoryViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PostStoryFragment : BaseFragment<FragmentPostStoryBinding>() {
    private val viewModel: PostStoryViewModel by viewModels()
    override fun createViewBinding() = FragmentPostStoryBinding.inflate(layoutInflater)

    companion object {
        fun open(navController: NavController) {
            navController.navigate(R.id.actPostStoryFragment)
        }
    }
}