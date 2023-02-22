package com.example.reading.presentation.view.popup

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.PopupWindow
import com.example.reading.databinding.PopupStoriesBinding
import com.example.reading.domain.model.Story
import com.example.reading.presentation.view.adapter.CallBack
import com.example.reading.presentation.view.adapter.StoryNameController

class StoryPopup(private val stories: List<Story>, private val context: Context) : PopupWindow() {
    private var binding: PopupStoriesBinding
    private val inflate by lazy { LayoutInflater.from(context) }
    private lateinit var controller: StoryNameController
    private lateinit var onItemClicked: (story: Story) -> Unit

    companion object {
        fun show(
            stories: List<Story>,
            context: Context,
            anchor: View,
            onItemClicked: (story: Story) -> Unit
        ) {
            val popup = StoryPopup(stories, context)
            popup.onItemClicked = onItemClicked
            popup.showAsDropDown(anchor)
        }
    }

    init {
        width = WindowManager.LayoutParams.WRAP_CONTENT
        height = WindowManager.LayoutParams.WRAP_CONTENT
        binding = PopupStoriesBinding.inflate(inflate, null, false)
        contentView = binding.root

        elevation = 32F
        isFocusable = true

        initializeComponent()
    }

    private fun initializeComponent() {
        controller = StoryNameController(object : CallBack {
            override fun onItemClicked(story: Story) {
                this@StoryPopup.onItemClicked(story)
                dismiss()
            }
        })

        binding.lstStory.setHasFixedSize(true)
        binding.lstStory.setController(controller)
        controller.setData(stories)
    }


}