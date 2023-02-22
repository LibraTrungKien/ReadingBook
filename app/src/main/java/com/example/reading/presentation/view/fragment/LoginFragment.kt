package com.example.reading.presentation.view.fragment

import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.reading.R
import com.example.reading.databinding.FragmentLoginBinding
import com.example.reading.presentation.view.base.BaseFragment
import com.example.reading.presentation.view.base.apiCall
import com.example.reading.presentation.viewmodel.LoginViewModel
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

    override fun initializeEvents() {
        binding.edtAccount.doAfterTextChanged { viewModel.copyEmail(it.toString()) }
        binding.edtPassword.doAfterTextChanged { viewModel.copyPassword(it.toString()) }
        binding.btnLogin.setOnClickListener { login() }
    }

    private fun login() {
        apiCall(viewModel.login(), viewLifecycleOwner, {

        }, { true })
    }
}