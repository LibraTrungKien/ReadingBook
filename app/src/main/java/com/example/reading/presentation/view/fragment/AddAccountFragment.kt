package com.example.reading.presentation.view.fragment

import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import com.example.reading.R
import com.example.reading.databinding.FragmentAddAccountBinding
import com.example.reading.presentation.view.base.BaseFragment
import com.example.reading.presentation.view.base.apiCall
import com.example.reading.presentation.view.diglog.MessageDialog
import com.example.reading.presentation.viewmodel.AddAccountViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddAccountFragment : BaseFragment<FragmentAddAccountBinding>() {
    private val viewModel: AddAccountViewModel by viewModels()
    override fun createViewBinding() = FragmentAddAccountBinding.inflate(layoutInflater)

    companion object {
        fun open(navController: NavController) {
            navController.navigate(R.id.actAddNewAccountFragment)
        }
    }

    override fun initializeEvents() {
        binding.edtUserName.doAfterTextChanged { viewModel.copyDisplayName(it.toString().trim()) }
        binding.edtGmail.doAfterTextChanged {
            viewModel.copyGmail(
                it.toString().trim()
            )
        }
        binding.edtPassword.doAfterTextChanged { viewModel.copyPassword(it.toString().trim()) }
        binding.edtAgainPassword.doAfterTextChanged {
            viewModel.copyAgainPassword(
                it.toString().trim()
            )
        }

        binding.btnNewAccount.setOnClickListener { registerAccount() }
    }

    private fun registerAccount() {
        if (!viewModel.validateData()) {
            showDialog("Cảnh báo", "Vui lòng nhập đủ thông tin!", R.drawable.ic_sad)
            return
        }

        if (!viewModel.checkPassword()) {
            showDialog("Cánh báo", "Password không trùng!", R.drawable.ic_sad)
            return
        }
        apiCall(viewModel.registerAccount(), viewLifecycleOwner, {
            showDialog("Thông báo", "Tạo tài khoản thành công", R.drawable.satisfied)
        }, { true })
    }

    private fun showDialog(title: String, content: String, icon: Int) {
        MessageDialog.show(
            parentFragmentManager,
            title,
            content,
            icon
        ) {}
    }

}