package com.example.reading.presentation.view.diglog

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.example.reading.R
import com.example.reading.databinding.DialogInfoAppBinding

class InfoAppDialog : DialogFragment() {
    private lateinit var binding: DialogInfoAppBinding

    companion object {
        fun show(
            fragmentManager: FragmentManager
        ) {
            val dialog = InfoAppDialog()
            dialog.show(fragmentManager, null)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogInfoAppBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initializeEvents()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return Dialog(
            requireContext(),
            R.style.CustomBottomSheetDialogTheme
        ).apply { setCanceledOnTouchOutside(false) }
    }

    private fun initializeEvents() {
        binding.btnClose.setOnClickListener {
            dismiss()

        }


    }

}