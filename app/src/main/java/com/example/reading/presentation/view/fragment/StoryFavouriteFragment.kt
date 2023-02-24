package com.example.reading.presentation.view.fragment

import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.reading.R
import com.example.reading.databinding.FragmentStoryFavouriteBinding
import com.example.reading.presentation.view.Interactor
import com.example.reading.presentation.view.adapter.StoryFavouriteController
import com.example.reading.presentation.view.base.BaseFragment
import com.example.reading.presentation.viewmodel.StoryFavouriteViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StoryFavouriteFragment : BaseFragment<FragmentStoryFavouriteBinding>() {

    companion object {
        fun open(navController: NavController) {
            navController.navigate(R.id.actStoryFavouriteFragment)
        }
    }

    private lateinit var controller: StoryFavouriteController
    private val viewModel: StoryFavouriteViewModel by viewModels()
    override fun createViewBinding() = FragmentStoryFavouriteBinding.inflate(layoutInflater)

    override fun initializeComponent() {
        binding.lstStory.setHasFixedSize(false)
        controller = StoryFavouriteController(object : Interactor {
            override fun findNavController() = this@StoryFavouriteFragment.findNavController()

            override fun isFromSearch() = false

            override fun getStory() = null

            override fun getViewModel() = viewModel

            override fun getFragment() = this@StoryFavouriteFragment
        })
        binding.lstStory.setController(controller)
    }

    override fun initializeEvents() {
        viewModel.dataLiveData.observe(viewLifecycleOwner) {
            controller.setData(it)
        }

        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun initializeData() {
        viewModel.loadData()
    }
}