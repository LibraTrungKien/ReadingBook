package com.example.reading.presentation.view.fragment

import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.reading.R
import com.example.reading.databinding.FragmentManagerStoryBinding
import com.example.reading.domain.model.Account
import com.example.reading.presentation.Key
import com.example.reading.presentation.view.adapter.ManagerStoryController
import com.example.reading.presentation.view.base.BaseFragment
import com.example.reading.presentation.viewmodel.ManagerStoryViewModel
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ManagerStoryFragment : BaseFragment<FragmentManagerStoryBinding>() {
    private val viewModel: ManagerStoryViewModel by viewModels()
    private lateinit var controller: ManagerStoryController

    companion object {
        fun open(navController: NavController, account: Account) {
            val bundle = bundleOf(Key.DATA to Gson().toJson(account))
            navController.navigate(R.id.actManagerStoryFragment, bundle)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.initializeArgument(requireArguments())
    }

    override fun initializeComponent() {
        binding.lstStory.setHasFixedSize(false)
        controller = ManagerStoryController()
        binding.lstStory.setController(controller)
    }

    override fun initializeEvents() {
        binding.toolbar.setNavigationOnClickListener { findNavController().popBackStack() }
        viewModel.dataLiveData.observe(viewLifecycleOwner) {
            controller.setData(it)
        }
    }

    override fun initializeData() {
        viewModel.getStories()
    }

    override fun createViewBinding() = FragmentManagerStoryBinding.inflate(layoutInflater)
}