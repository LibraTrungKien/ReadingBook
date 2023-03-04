package com.example.reading.presentation.view.fragment

import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.reading.R
import com.example.reading.databinding.FragmentRegisterBinding
import com.example.reading.presentation.view.base.BaseFragment
import com.example.reading.presentation.view.base.apiCall
import com.example.reading.presentation.view.base.visibleOrGone
import com.example.reading.presentation.view.diglog.HandleOtpDialog
import com.example.reading.presentation.view.diglog.MessageDialog
import com.example.reading.presentation.viewmodel.RegisterViewModel
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class RegisterFragment : BaseFragment<FragmentRegisterBinding>() {
    private val viewModel: RegisterViewModel by viewModels()
    private lateinit var auth: FirebaseAuth
    private val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        override fun onVerificationCompleted(credential: PhoneAuthCredential) {}

        override fun onVerificationFailed(e: FirebaseException) {
            MessageDialog.show(
                parentFragmentManager,
                requireContext().getString(R.string.notification),
                "Không thành công!Vui lòng nhập đúng định dạng số điện thoại!",
                R.drawable.ic_sad,
                {})
        }

        override fun onCodeSent(
            verificationId: String,
            token: PhoneAuthProvider.ForceResendingToken
        ) {
            bindViewProgress(false)
            HandleOtpDialog.show(parentFragmentManager, verificationId)
        }
    }

    override fun createViewBinding() = FragmentRegisterBinding.inflate(layoutInflater)

    companion object {
        fun open(navController: NavController) {
            navController.navigate(R.id.actRegisterFragment)
        }
    }

    override fun initializeComponent() {
        auth = FirebaseAuth.getInstance().apply {
            this.useAppLanguage()
        }
    }

    override fun initializeEvents() {
        binding.edtName.doAfterTextChanged { viewModel.copyName(it.toString().trim()) }

        binding.tilPhoneNumber.setEndIconOnClickListener {
            bindViewProgress(true)
            verifyPhoneNumber()
        }
        binding.edtPhoneNumber.doAfterTextChanged {
            viewModel.copyPhoneNumber(
                it.toString().trim()
            )
        }

        binding.edtPassword.doAfterTextChanged { viewModel.copyPassword(it.toString().trim()) }
        binding.edtAgainPassword.doAfterTextChanged {
            viewModel.copyAgainPassword(
                it.toString().trim()
            )
        }
        binding.rdgGender.setOnCheckedChangeListener { _, checkedId ->
            val gender =
                if (checkedId == R.id.rdbMale) requireContext().getString(R.string.male) else requireContext().getString(
                    R.string.female
                )
            viewModel.copyGender(gender)
        }

        binding.btnRegister.setOnClickListener { handleClickRegister() }
    }


    override fun bindView() {
        bindViewProgress(false)
    }

    private fun bindViewProgress(isVisible: Boolean) {
        binding.prgIndicator.visibleOrGone(isVisible)
    }

    private fun verifyPhoneNumber() {
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(viewModel.account.phone)       // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(requireActivity())                 // Activity (for callback binding)
            .setCallbacks(callbacks)          // OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    private fun handleClickRegister() {
        if (!validateData()) return

        bindViewProgress(true)
        apiCall(viewModel.registerAccount(), viewLifecycleOwner, {
            bindViewProgress(false)
            MessageDialog.show(
                parentFragmentManager,
                requireContext().getString(R.string.congratulation),
                requireContext().getString(R.string.register_success),
                R.drawable.satisfied
            ) {
                findNavController().popBackStack()
            }
        }, { true })
    }

    private fun validateData(): Boolean {
        if (!viewModel.validateData()) {
            showMessageDialog(
                requireContext().getString(R.string.warning),
                requireContext().getString(R.string.enter_all_field),
                R.drawable.ic_sad
            ) {}
            return false
        }
        if (!viewModel.checkDuplicate()) {
            showMessageDialog(
                requireContext().getString(R.string.warning),
                requireContext().getString(R.string.password_not_duplicate),
                R.drawable.ic_sad
            ) {}
            return false
        }
        return true
    }
}