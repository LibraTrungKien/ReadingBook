package com.example.reading.presentation.view.diglog

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.example.reading.R
import com.example.reading.databinding.DialogMessageBinding

class MessageDialog(
    private val title: String,
    private val content: String,
    private val image: Int,
    private val onButtonClicked: () -> Unit
) : DialogFragment() {
    private lateinit var binding: DialogMessageBinding

    companion object {
        fun show(
            fragmentManager: FragmentManager,
            title: String,
            content: String,
            image: Int,
            onButtonClicked: () -> Unit
        ) {
            val dialog = MessageDialog(title, content, image, onButtonClicked)
            dialog.show(fragmentManager, null)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogMessageBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initializeEvents()
        bindView()
    }

    private fun bindView() {
        binding.txtTitle.text = title
        binding.txtContent.text = content
        binding.imgStatus.setImageResource(image)
    }

    private fun initializeEvents() {
        binding.btnOk.setOnClickListener {
            dismiss()
            onButtonClicked()
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return Dialog(requireContext(), R.style.CustomBottomSheetDialogTheme)
    }
}