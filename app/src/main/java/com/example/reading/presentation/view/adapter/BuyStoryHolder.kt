package com.example.reading.presentation.view.adapter

import com.bumptech.glide.Glide
import com.example.reading.R
import com.example.reading.databinding.ItemImageStoryBinding
import com.example.reading.databinding.ItemStoryFavouriteBinding
import com.example.reading.domain.model.Story
import com.example.reading.presentation.view.base.ViewBindingEpoxyModelWithHolder
import com.example.reading.presentation.view.fragment.StoryFragment

class BuyStoryHolder(
    private val story: Story,
    private val interactor: BuyStoryController.Interactor
) : ViewBindingEpoxyModelWithHolder<ItemImageStoryBinding>() {
    override fun getDefaultLayout() = R.layout.item_image_story

    override fun initializeEvents() {
        binding.root.setOnClickListener {
            StoryFragment.open(interactor.findNavController(), story,)
        }
    }

    override fun bindView() {
        binding.txtName.text = story.name
        Glide.with(binding.imgStory).load(story.image).into(binding.imgStory)
    }
}