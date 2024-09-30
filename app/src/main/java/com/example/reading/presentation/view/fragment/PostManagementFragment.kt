package com.example.reading.presentation.view.fragment

import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.reading.R
import com.example.reading.databinding.FragmentPostManagementBinding
import com.example.reading.presentation.view.adapter.PostManagementAdapter
import com.example.reading.presentation.view.base.BaseFragment
import com.example.reading.presentation.viewmodel.PostManagementViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PostManagementFragment: BaseFragment<FragmentPostManagementBinding>() {

    private val viewmodel: PostManagementViewModel by viewModels()
    private val adapter: PostManagementAdapter = PostManagementAdapter()

    companion object {
        fun open(navController: NavController) {
            navController.navigate(R.id.actPostManagementFragment)
        }
    }

    override fun createViewBinding() = FragmentPostManagementBinding.inflate(layoutInflater)

    override fun initializeComponent() {
        binding.lstPost.setHasFixedSize(false)
        binding.lstPost.adapter = adapter
    }

    override fun initializeEvents() {
        binding.toolbar.setNavigationOnClickListener { findNavController().popBackStack() }

        viewmodel.dataLiveData.observe(this){
            adapter.data = it
        }

        adapter.onItemClicked = {
            story -> StoryDetailFragment.open(findNavController(), story, story.chapters.last().index)
        }
        
        adapter.onApproveClicked = { story ->
            story.status = 1
            story.dateUpdated = System.currentTimeMillis()
            viewmodel.updateStatusStory(story)
        }
        
        adapter.onDeclineClicked = { story ->
            story.status = 0
            story.dateUpdated = System.currentTimeMillis()
            viewmodel.updateStatusStory(story)
        }
    }

    override fun initializeData() {
        viewmodel.getStoryNotYetApprove()
    }
}