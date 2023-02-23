package com.example.reading.presentation.view.fragment

import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.reading.R
import com.example.reading.databinding.FragmentLoginBinding
import com.example.reading.presentation.view.diglog.MessageDialog
import com.example.reading.presentation.view.base.BaseFragment
import com.example.reading.presentation.view.base.apiCall
import com.example.reading.presentation.view.base.visibleOrGone
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

    override fun initializeComponent() {
        binding.prgIndicator.visibleOrGone(false)
    }

    override fun initializeEvents() {
        binding.edtAccount.doAfterTextChanged { viewModel.copyEmail(it.toString()) }
        binding.edtPassword.doAfterTextChanged { viewModel.copyPassword(it.toString()) }
        binding.btnLogin.setOnClickListener { handleLogin() }
    }

    private fun handleLogin() {
        binding.prgIndicator.visibleOrGone(true)
        apiCall(viewModel.login(), viewLifecycleOwner, {
            handleLoginSuccess()
        }, {
            binding.prgIndicator.visibleOrGone(false)
            true
        })
    }

    private fun handleLoginSuccess() {
        binding.prgIndicator.visibleOrGone(false)
        MessageDialog.show(
            parentFragmentManager,
            "Chúc mừng",
            "Đăng nhập thành công",
            R.drawable.satisfied
        ) {
            MainFragment.open(findNavController())
        }
    }
}