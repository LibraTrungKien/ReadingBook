package com.example.reading.presentation.view.fragment

import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.reading.R
import com.example.reading.databinding.FragmentStoryBinding
import com.example.reading.domain.model.Story
import com.example.reading.presentation.Key
import com.example.reading.presentation.view.adapter.ChapterController
import com.example.reading.presentation.view.adapter.Interactor
import com.example.reading.presentation.view.base.BaseFragment
import com.example.reading.presentation.viewmodel.StoryViewModel
import com.google.gson.Gson

class StoryFragment : BaseFragment<FragmentStoryBinding>() {
    private val viewModel: StoryViewModel by viewModels()
    private lateinit var controller: ChapterController

    companion object {
        fun open(navController: NavController, story: Story) {
            val bundle = bundleOf(Key.DATA to Gson().toJson(story))
            navController.navigate(R.id.actStoryFragment, bundle)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.initializeArgument(requireArguments())
    }

    override fun createViewBinding() = FragmentStoryBinding.inflate(layoutInflater)

    override fun initializeComponent() {
        controller = ChapterController(object : Interactor {
            override fun findNavController() = this@StoryFragment.findNavController()
            override fun isFromSearch() = false
            override fun getStory() = viewModel.story
            override fun getViewModel() = viewModel
        })

        binding.lstChapter.setHasFixedSize(false)
        binding.lstChapter.setController(controller)
        controller.setData(viewModel.story.chapters)
    }

    override fun initializeEvents() {
        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        binding.btnRead.setOnClickListener {
            StoryDetailFragment.open(
                findNavController(),
                viewModel.story,
                1
            )
        }
    }

    override fun bindView() {
        val story = viewModel.story
        Glide.with(binding.imgStory).load(story.image).into(binding.imgStory)
        binding.txtAuthor.text = story.author
        binding.toolbar.title = story.name
        binding.txtCreatedDate.text = story.dateCreated
        binding.txtDescription.text = story.description
        binding.txtStatus.text = story.status
        binding.txtCategory.text = story.getCategory()
        binding.txtChapter.text = "${story.chapters.size}"
    }

}