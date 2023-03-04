package com.example.reading.presentation.view.diglog

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.example.reading.R
import com.example.reading.databinding.DialogHandleOtpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider

class HandleOtpDialog(
    private val verificationId: String
) : DialogFragment() {
    private lateinit var binding: DialogHandleOtpBinding
    private lateinit var code: String
    private lateinit var auth: FirebaseAuth
    private lateinit var isSuccess: () -> Unit

    companion object {
        fun show(
            fragmentManager: FragmentManager,
            verificationId: String,
            isSuccess: () -> Unit
        ) {
            val dialog = HandleOtpDialog(verificationId)
            dialog.show(fragmentManager, null)
            dialog.isSuccess = isSuccess
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogHandleOtpBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initializeComponent()
        initializeEvents()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return Dialog(
            requireContext(),
            R.style.CustomBottomSheetDialogTheme
        ).apply { setCanceledOnTouchOutside(false) }
    }

    private fun initializeComponent() {
        auth = FirebaseAuth.getInstance()
    }

    private fun initializeEvents() {
        binding.edtOtp.doAfterTextChanged {
            code = it.toString().trim()
        }

        binding.btnSend.setOnClickListener {
            sentOtp()
        }

    }

    private fun sentOtp() {
        val credential = PhoneAuthProvider.getCredential(verificationId, code)
        signInWithPhoneAuthCredential(credential)
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    MessageDialog.show(
                        parentFragmentManager,
                        requireContext().getString(R.string.success),
                        "Xác nhận thành công",
                        R.drawable.satisfied
                    ) {}
                    isSuccess()
                    dismiss()
                } else {
                    MessageDialog.show(
                        parentFragmentManager,
                        requireContext().getString(R.string.failed),
                        " Xác nhận không thành công",
                        R.drawable.ic_sad
                    ) {
                    }
                    dismiss()
                }
            }
    }
}