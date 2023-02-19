package com.example.reading.presentation.view.adapter

import com.airbnb.epoxy.TypedEpoxyController
import com.bumptech.glide.Glide
import com.example.reading.R
import com.example.reading.databinding.ItemImageStoryBinding
import com.example.reading.domain.model.Story
import com.example.reading.presentation.view.fragment.StoryFragment
import com.example.reading.presentation.view.base.ViewBindingEpoxyModelWithHolder

class StoryController(private val interactor: Interactor) : TypedEpoxyController<List<Story>>() {
    override fun buildModels(data: List<Story>?) {
        data ?: return
        data.forEach {
            ViewHolder(interactor, it).id(it.hashCode()).addTo(this)
        }
    }

    class ViewHolder(
        private val interactor: Interactor,
        private val story: Story
    ) :
        ViewBindingEpoxyModelWithHolder<ItemImageStoryBinding>() {
        override fun getDefaultLayout() = R.layout.item_image_story
        override fun initializeEvents() {
            binding.root.setOnClickListener {
                StoryFragment.open(interactor.findNavController(), story = story)
            }
        }

        override fun bindView() {
            Glide.with(binding.imgStory).load(story.image).into(binding.imgStory)
            binding.txtName.text = story.name
        }
    }
}