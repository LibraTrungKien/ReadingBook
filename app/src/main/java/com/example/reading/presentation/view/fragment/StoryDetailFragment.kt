package com.example.reading.presentation.view.fragment

import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.reading.R
import com.example.reading.databinding.FragmentStoryDetailBinding
import com.example.reading.domain.model.Chapter
import com.example.reading.domain.model.Story
import com.example.reading.presentation.Key
import com.example.reading.presentation.view.base.BaseFragment
import com.example.reading.presentation.view.base.visibleOrGone
import com.example.reading.presentation.viewmodel.StoryDetailViewModel
import com.google.gson.Gson

class StoryDetailFragment : BaseFragment<FragmentStoryDetailBinding>() {
    private val viewModel: StoryDetailViewModel by viewModels()

    companion object {
        fun open(navController: NavController, story: Story, index: Int) {
            val bundle = bundleOf(
                Key.DATA to Gson().toJson(story),
                Key.INDEX to index
            )
            navController.navigate(R.id.actStoryDetailFragment, bundle)
        }
    }

    override fun createViewBinding() = FragmentStoryDetailBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.initializeArguments(requireArguments())
    }

    override fun initializeEvents() {
        binding.toolbar.setNavigationOnClickListener { findNavController().popBackStack() }
        binding.btnNext.setOnClickListener { viewModel.nextChap() }
        binding.btnPrevious.setOnClickListener { viewModel.previousChap() }

        viewModel.dataLiveData.observe(viewLifecycleOwner) {
            bindViewTitle(it)
            bindViewContent(it)
            bindViewImageChap(it)
            bindViewPrevious()
            bindViewNext()
        }
    }

    override fun bindView() {
        val chapter = viewModel.currentChap
        bindViewTitle(chapter)
        bindViewContent(chapter)
        bindViewImageChap(chapter)
        bindViewPrevious()
        bindViewNext()
    }

    private fun bindViewTitle(chapter: Chapter) {
        binding.toolbar.title = chapter.title
    }

    private fun bindViewContent(chapter: Chapter) {
        binding.txtContent.text = chapter.content
    }

    private fun bindViewImageChap(chapter: Chapter) {
        Glide.with(requireActivity())
            .load(chapter.image.ifBlank { viewModel.story.image })
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .skipMemoryCache(true)
            .into(binding.imgChapter)
    }

    private fun bindViewNext() {
        binding.btnNext.visibleOrGone(!viewModel.isEndChap())
    }

    private fun bindViewPrevious() {
        binding.btnPrevious.visibleOrGone(!viewModel.isFirstChap())
    }
}