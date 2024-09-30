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
    private val binding: ToolbarCustomBinding
    private val actionIndex: Int

    enum class ActionIcon {
        SETTING,
        DONE
    }

    init {

        val inflater = LayoutInflater.from(context)

        binding = ToolbarCustomBinding.inflate(inflater, this, true)
        binding.root
        context.theme.obtainStyledAttributes(attrs, R.styleable.MyToolbar, 0, 0).apply {
            try {
                val title = getString(R.styleable.MyToolbar_title)
                actionIndex = getInt(R.styleable.MyToolbar_actionIcon, 1)

                binding.btnBack.setImageResource(R.drawable.ic_back)

                binding.txtTitle.text = title

                bindViewAction()
            } finally {
                recycle()
            }
        }
    }

    private fun bindViewAction() {
        val resource = when (actionIndex) {
            1 -> {
                R.drawable.ic_setting
            }

            2 -> {
                R.drawable.ic_done
            }

            else -> {
                R.drawable.ic_edit
            }
        }
        binding.btnAction.setImageResource(resource)
    }

    fun setOnNavigationClick(onNavigationClicked: () -> Unit) {
        binding.btnBack.setOnClickListener { onNavigationClicked() }
    }

    fun setOnActionClick(onActionClick: () -> Unit) {
        binding.btnAction.setOnClickListener { onActionClick() }
    }

}