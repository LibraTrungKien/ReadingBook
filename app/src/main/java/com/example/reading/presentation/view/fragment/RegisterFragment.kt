package com.example.reading.presentation.view.fragment

import androidx.core.view.isGone
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.reading.R
import com.example.reading.databinding.FragmentRegisterBinding
import com.example.reading.presentation.view.base.BaseFragment
import com.example.reading.presentation.view.base.apiCall
import com.example.reading.presentation.view.base.visibleOrGone
import com.example.reading.presentation.view.diglog.MessageDialog
import com.example.reading.presentation.viewmodel.RegisterViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : BaseFragment<FragmentRegisterBinding>() {
    private val viewModel: RegisterViewModel by viewModels()

    override fun createViewBinding() = FragmentRegisterBinding.inflate(layoutInflater)

    companion object {
        fun open(navController: NavController) {
            navController.navigate(R.id.actRegisterFragment)
        }
    }

    override fun initializeComponent() {
    }

    override fun initializeEvents() {
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.edtName.doAfterTextChanged { viewModel.copyName(it.toString().trim()) }

        binding.edtGmail.doAfterTextChanged {
            viewModel.copyEmail(
                it.toString().trim()
            )
        }

        binding.edtPassword.doAfterTextChanged { viewModel.copyPassword(it.toString().trim()) }
        binding.edtAgainPassword.doAfterTextChanged {
            viewModel.copyAgainPassword(
                it.toString().trim()
            )
        }

        binding.btnRegister.setOnClickListener { handleClickRegister() }
    }


    override fun bindView() {
        bindViewProgress(false)
    }

    private fun bindViewProgress(isVisible: Boolean) {
        binding.prgIndicator.visibleOrGone(isVisible)
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
                bindViewProgress(false)
                findNavController().popBackStack()
            }
        }, {
            bindViewProgress(false)
            true
        })
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