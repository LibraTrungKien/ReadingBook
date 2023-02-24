package com.example.reading.presentation.view.adapter

import com.airbnb.epoxy.TypedEpoxyController
import com.example.reading.presentation.model.StoryModelHolder
import com.example.reading.presentation.model.Type
import com.example.reading.presentation.view.Interactor

class CategoryController(private val interactor: Interactor) :
    TypedEpoxyController<List<StoryModelHolder>>() {
    override fun buildModels(data: List<StoryModelHolder>?) {
        data ?: return
        data.forEach {
            buildModel(it)
            if (it.subItem != null) {
                buildModel(it.subItem!!)
            }
        }
    }

    private fun buildModel(storyModelHolder: StoryModelHolder) {
        when (storyModelHolder.type) {
            Type.HEADER -> HeaderHolder(storyModelHolder.name).id(storyModelHolder.hashCode())
                .addTo(this)

            else -> StoryHolder(interactor, storyModelHolder).id(storyModelHolder.hashCode())
                .addTo(this)
        }
    }

}