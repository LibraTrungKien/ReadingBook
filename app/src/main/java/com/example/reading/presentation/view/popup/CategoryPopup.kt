package com.example.reading.presentation.view.popup

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.PopupWindow
import com.example.reading.databinding.PopupStoriesBinding
import com.example.reading.presentation.view.adapter.CategoryControllerV2
import com.example.reading.presentation.view.adapter.ItemOnClickListener


class CategoryPopup(private val categories: List<String>, private val context: Context) :
    PopupWindow() {
    private var binding: PopupStoriesBinding
    private val inflate by lazy { LayoutInflater.from(context) }
    private lateinit var controller: CategoryControllerV2
    private lateinit var onItemClicked: (category: Int) -> Unit

    companion object {
        fun show(
            categories: List<String>,
            context: Context,
            anchor: View,
            onItemClicked: (category: Int) -> Unit
        ) {
            val popup = CategoryPopup(categories, context)
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
        controller = CategoryControllerV2(object : ItemOnClickListener {
            override fun onItemClicked(category: Int) {
                this@CategoryPopup.onItemClicked(category)
                dismiss()
            }
        })

        binding.lstStory.setHasFixedSize(true)
        binding.lstStory.setController(controller)
        controller.setData(categories)
    }

}