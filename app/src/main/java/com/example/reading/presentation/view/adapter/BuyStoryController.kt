package com.example.reading.presentation.view.adapter

import androidx.navigation.NavController
import com.airbnb.epoxy.TypedEpoxyController
import com.example.reading.domain.model.Story

class BuyStoryController(private val interactor: Interactor) : TypedEpoxyController<List<Story>>() {
    override fun buildModels(data: List<Story>?) {
        data?.let {
            it.forEach { story ->
                buildModel(story, interactor)
            }
        }
    }

    private fun buildModel(story: Story, interactor: Interactor) {
        BuyStoryHolder(story, interactor).id(story.hashCode()).addTo(this)
    }

    interface Interactor{
        fun findNavController(): NavController
    }
}