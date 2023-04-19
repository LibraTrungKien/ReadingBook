package com.example.reading.presentation.view.fragment

import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.reading.R
import com.example.reading.databinding.FragmentBuyStoryBinding
import com.example.reading.presentation.view.adapter.BuyStoryController
import com.example.reading.presentation.view.base.BaseFragment
import com.example.reading.presentation.view.base.apiCall
import com.example.reading.presentation.viewmodel.BuyStoryViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BuyStoryFragment : BaseFragment<FragmentBuyStoryBinding>() {
    private lateinit var controller: BuyStoryController
    private val viewModel: BuyStoryViewModel by viewModels()

    override fun createViewBinding() = FragmentBuyStoryBinding.inflate(layoutInflater)

    companion object {
        fun open(navController: NavController) {
            navController.navigate(R.id.actBuyStoryFragment)
        }
    }

    override fun initializeComponent() {
        controller = BuyStoryController(object : BuyStoryController.Interactor {
            override fun findNavController(): NavController {
                return this@BuyStoryFragment.findNavController()
            }

        })
        binding.lstStory.setHasFixedSize(false)
        binding.lstStory.setController(controller)
    }

    override fun initializeEvents() {
        viewModel.dataLiveData.observe(viewLifecycleOwner) {
            controller.setData(it)
        }

        binding.toolbar.setNavigationOnClickListener { findNavController().popBackStack() }
    }

    override fun initializeData() {
        apiCall(viewModel.getData(), viewLifecycleOwner, {}, { true })
    }
}