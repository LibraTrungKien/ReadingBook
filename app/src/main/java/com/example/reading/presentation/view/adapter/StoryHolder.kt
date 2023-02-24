package com.example.reading.presentation.view.adapter

import com.example.reading.R
import com.example.reading.databinding.HolderStoryBinding
import com.example.reading.presentation.model.StoryModelHolder
import com.example.reading.presentation.view.Interactor
import com.example.reading.presentation.view.base.ViewBindingEpoxyModelWithHolder

class StoryHolder(
    private val interactor: Interactor,
    private val storyModelHolder: StoryModelHolder) :
    ViewBindingEpoxyModelWithHolder<HolderStoryBinding>() {
    private val controller by lazy {
        StoryController(interactor)
    }

    override fun getDefaultLayout() = R.layout.holder_story

    override fun initializeComponent() {
        binding.lstStory.setController(controller)
        binding.lstStory.setHasFixedSize(false)
        controller.setData(storyModelHolder.stories)
    }
}