package com.example.reading.presentation.view.custom

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.appcompat.widget.Toolbar
import com.example.reading.R
import com.example.reading.databinding.ToolbarCustomBinding

class MyToolbar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet,
    defStyleAttr: Int = 0
) :
    Toolbar(context, attrs, defStyleAttr) {
    val binding: ToolbarCustomBinding

    init {

        val inflater = LayoutInflater.from(context)

        binding = ToolbarCustomBinding.inflate(inflater, this, true)
        binding.root
        context.theme.obtainStyledAttributes(attrs, R.styleable.MyToolbar, 0, 0).apply {
            try {
                val title = getString(R.styleable.MyToolbar_title)
                binding.btnBack.setImageResource(R.drawable.ic_back)

                binding.txtTitle.text = title

                binding.btnAction.setImageResource(R.drawable.ic_setting)
            } finally {
                recycle()
            }
        }
    }

    fun setOnNavigationClick(onNavigationClicked: () -> Unit) {
        binding.btnBack.setOnClickListener { onNavigationClicked() }
    }

    fun setOnActionClick(onActionClick: () -> Unit) {
        binding.btnAction.setOnClickListener { onActionClick() }
    }

}