package com.example.reading.presentation.view.fragment

import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.reading.R
import com.example.reading.databinding.FragmentLoginBinding
import com.example.reading.presentation.view.base.BaseFragment
import com.example.reading.presentation.view.base.apiCall
import com.example.reading.presentation.view.base.visibleOrGone
import com.example.reading.presentation.view.diglog.MessageDialog
import com.example.reading.presentation.viewmodel.LoginViewModel
import com.google.android.material.textfield.TextInputLayout
import com.google.android.material.textfield.TextInputLayout.END_ICON_NONE
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>() {
    override fun createViewBinding() = FragmentLoginBinding.inflate(layoutInflater)
    private val viewModel: LoginViewModel by viewModels()

    companion object {
        fun open(navController: NavController) {
            navController.navigate(R.id.actLoginFragment)
        }
    }

    override fun initializeComponent() {
        bindViewProgress(false)
    }

    override fun initializeEvents() {
        binding.edtPhoneNumber.doAfterTextChanged {
            viewModel.copyPhoneNumber(
                it.toString().trim()
            )
        }
        binding.edtPassword.doAfterTextChanged {
            binding.tilPassword.endIconMode = TextInputLayout.END_ICON_PASSWORD_TOGGLE
            viewModel.copyPassword(it.toString().trim())
        }
        binding.btnLogin.setOnClickListener { handleLogin() }
        binding.btnRegister.setOnClickListener { openRegisterFragment() }
    }

    private fun handleLogin() {
        if (!validateData())
            return

        bindViewProgress(true)
        apiCall(viewModel.login(), viewLifecycleOwner, {
            MainFragment.open(findNavController())
        }, {
            bindViewProgress(false)
            true
        })
    }

    private fun validateData(): Boolean {
        if (viewModel.login.phone.isBlank()) {
            binding.edtPhoneNumber.error =
                requireContext().getString(R.string.enter_username_please)
            return false
        }

        if (viewModel.login.password.isBlank()) {
            binding.tilPassword.endIconMode = END_ICON_NONE
            binding.edtPassword.error = requireContext().getString(R.string.enter_password_please)
            return false
        }
        return true
    }

    private fun openRegisterFragment() {
        RegisterFragment.open(findNavController())
    }

    private fun bindViewProgress(isVisible: Boolean) {
        binding.prgIndicator.visibleOrGone(isVisible)
    }
}