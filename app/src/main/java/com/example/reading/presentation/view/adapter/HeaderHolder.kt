package com.example.reading.presentation.view.adapter

import com.example.reading.R
import com.example.reading.databinding.ItemHeaderBinding
import com.example.reading.presentation.view.base.ViewBindingEpoxyModelWithHolder

class HeaderHolder(private val title: String): ViewBindingEpoxyModelWithHolder<ItemHeaderBinding>() {
    override fun getDefaultLayout() = R.layout.item_header

    override fun bindView() {
        binding.txtCategory.text = title
    }
}