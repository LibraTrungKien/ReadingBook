package com.example.reading.presentation.view.diglog

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.example.reading.R
import com.example.reading.databinding.DialogConfirmBinding


class ConfirmDialog(
    private val title: String,
    private val content: String,
    private val onButtonClicked: () -> Unit
) : DialogFragment() {
    private lateinit var binding: DialogConfirmBinding

    companion object {
        fun show(
            fragmentManager: FragmentManager,
            title: String,
            content: String,
            onButtonClicked: () -> Unit
        ) {
            val dialog = ConfirmDialog(title, content, onButtonClicked)
            dialog.show(fragmentManager, null)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogConfirmBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initializeEvents()
        bindView()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return Dialog(requireContext(), R.style.CustomBottomSheetDialogTheme)
    }

    private fun bindView() {
        binding.txtTitle.text = title
        binding.txtContent.text = content
    }

    private fun initializeEvents() {
        binding.btnAgree.setOnClickListener {
            dismiss()
            onButtonClicked()
        }

        binding.btnCancel.setOnClickListener { dismiss() }
    }

}