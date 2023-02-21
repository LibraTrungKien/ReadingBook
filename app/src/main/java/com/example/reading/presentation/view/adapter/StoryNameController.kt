package com.example.reading.presentation.view.adapter

import com.airbnb.epoxy.TypedEpoxyController
import com.example.reading.R
import com.example.reading.databinding.ItemStoryNameBinding
import com.example.reading.domain.model.Story
import com.example.reading.presentation.view.base.ViewBindingEpoxyModelWithHolder

class StoryNameController(private val callBack: CallBack) : TypedEpoxyController<List<Story>>() {
    override fun buildModels(data: List<Story>?) {
        data ?: return
        data.forEach {
            Holder(callBack , it).id(it.hashCode()).addTo(this)
        }
    }

    class Holder(
        private val callBack: CallBack,
        private val story: Story
    ) :
        ViewBindingEpoxyModelWithHolder<ItemStoryNameBinding>() {
        override fun getDefaultLayout() = R.layout.item_story_name

        override fun initializeEvents() {
            binding.root.setOnClickListener { callBack.onItemClicked(story) }
        }

        override fun bindView() {
            binding.txtStoryName.text = story.name
        }
    }
}

interface CallBack {
    fun onItemClicked(story: Story)
}