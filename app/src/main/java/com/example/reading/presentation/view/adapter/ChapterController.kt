package com.example.reading.presentation.view.adapter

import com.airbnb.epoxy.TypedEpoxyController
import com.example.reading.R
import com.example.reading.databinding.ItemChapterBinding
import com.example.reading.domain.model.Chapter
import com.example.reading.presentation.view.Interactor
import com.example.reading.presentation.view.base.ViewBindingEpoxyModelWithHolder
import com.example.reading.presentation.view.fragment.StoryDetailFragment

class ChapterController(
    private val interactor: Interactor
) : TypedEpoxyController<List<Chapter>>() {
    override fun buildModels(data: List<Chapter>?) {
        data ?: return
        data.forEach {
            Holder(interactor, it).id(it.hashCode()).addTo(this)
        }
    }

    class Holder(
        private val interactor: Interactor,
        private val chapter: Chapter
    ) :
        ViewBindingEpoxyModelWithHolder<ItemChapterBinding>() {
        override fun getDefaultLayout() = R.layout.item_chapter

        override fun initializeEvents() {
            binding.root.setOnClickListener {
                StoryDetailFragment.open(
                    interactor.findNavController(),
                    interactor.getStory()!!,
                    chapter.index
                )
            }
        }

        override fun bindView() {
            binding.txtChapter.text = "Tập ${chapter.index}"
        }
    }
}