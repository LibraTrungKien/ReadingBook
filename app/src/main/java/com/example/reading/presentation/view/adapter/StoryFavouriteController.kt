package com.example.reading.presentation.view.adapter

import com.airbnb.epoxy.TypedEpoxyController
import com.bumptech.glide.Glide
import com.example.reading.R
import com.example.reading.databinding.ItemStoryFavouriteBinding
import com.example.reading.domain.model.Story
import com.example.reading.presentation.view.Interactor
import com.example.reading.presentation.view.base.ViewBindingEpoxyModelWithHolder
import com.example.reading.presentation.view.diglog.ConfirmDialog
import com.example.reading.presentation.view.fragment.StoryFragment
import com.example.reading.presentation.viewmodel.StoryFavouriteViewModel

class StoryFavouriteController(private val interactor: Interactor) :
    TypedEpoxyController<List<Story>>() {
    override fun buildModels(data: List<Story>?) {
        data ?: return
        data.forEach { Holder(interactor, it).id(it.hashCode()).addTo(this) }
    }

    class Holder(
        private val interactor: Interactor,
        private val story: Story
    ) : ViewBindingEpoxyModelWithHolder<ItemStoryFavouriteBinding>() {
        override fun getDefaultLayout() = R.layout.item_story_favourite

        override fun initializeEvents() {
            binding.root.setOnClickListener {
                StoryFragment.open(
                    interactor.findNavController(),
                    story
                )
            }

            binding.btnRemove.setOnClickListener {
                ConfirmDialog.show(
                    interactor.getFragment().parentFragmentManager,
                    "Xác nhận",
                    "Bạn có chắc chắn muốn xóa?"
                ) {
                    (interactor.getViewModel() as StoryFavouriteViewModel).deleteStoryFavourite(
                        story
                    )
                }
            }
        }

        override fun bindView() {
            Glide.with(binding.imgStory).load(story.image).into(binding.imgStory)
            binding.txtName.text = story.name
        }
    }
}