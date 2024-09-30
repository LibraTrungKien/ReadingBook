package com.example.reading.presentation.view.adapter

import androidx.navigation.NavController
import com.airbnb.epoxy.TypedEpoxyController
import com.bumptech.glide.Glide
import com.example.reading.R
import com.example.reading.databinding.ItemStoryFavouriteBinding
import com.example.reading.domain.model.Story
import com.example.reading.presentation.view.base.ViewBindingEpoxyModelWithHolder

class ManagerStoryController(private val interactor: Interactor) :
    TypedEpoxyController<List<Story>>() {
    override fun buildModels(data: List<Story>?) {
        data ?: return
        data.forEach {
            Holder(interactor, it).id(it.hashCode()).addTo(this)
        }
    }

    class Holder(private val interactor: Interactor, private val story: Story) :
        ViewBindingEpoxyModelWithHolder<ItemStoryFavouriteBinding>() {
        override fun getDefaultLayout() = R.layout.item_story_favourite

        override fun initializeEvents() {
            binding.btnRemove.setOnClickListener {
                interactor.deleteStory(story)
            }
        }

        override fun bindView() {
            binding.txtName.text = story.name
            binding.imgStory.alpha = if (story.status == 1) 1f else 0.5f
            Glide.with(binding.imgStory).load(story.image).into(binding.imgStory)
        }
    }
}

interface Interactor {
    fun getNavController(): NavController
    fun deleteStory(story: Story)
}