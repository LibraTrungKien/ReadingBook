package com.example.reading.presentation.view.adapter

import com.airbnb.epoxy.TypedEpoxyController
import com.example.reading.R
import com.example.reading.databinding.ItemStoryNameBinding
import com.example.reading.presentation.model.Category
import com.example.reading.presentation.view.base.ViewBindingEpoxyModelWithHolder

class CategoryControllerV2(private val itemOnClickListener: ItemOnClickListener) :
    TypedEpoxyController<List<String>>() {
    override fun buildModels(data: List<String>?) {
        data ?: return
        data.forEach {
            Holder(itemOnClickListener, it).id(it.hashCode()).addTo(this)
        }
    }

    class Holder(
        private val itemOnClickListener: ItemOnClickListener,
        private val category: String
    ) :
        ViewBindingEpoxyModelWithHolder<ItemStoryNameBinding>() {
        override fun getDefaultLayout() = R.layout.item_story_name

        override fun initializeView() {
            binding.root.setOnClickListener {
                val category = getCategory(category)
                itemOnClickListener.onItemClicked(category)
            }
        }

        override fun bindView() {
            binding.txtStoryName.text = category
        }

        private fun getCategory(category: String): Int {
            return when (category) {
                "Truyện ma" -> Category.GHOST
                "Truyện cổ tích thế giới" -> Category.FAIRY_TALES
                "Truyện cười" -> Category.JOKES
                "Truyện cổ tích Việt Nam" -> Category.FAIRY_TALES_VI
                else -> Category.FOLK_TALE
            }
        }
    }
}

interface ItemOnClickListener {
    fun onItemClicked(category: Int)
}