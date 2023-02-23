package com.example.reading.presentation.view.fragment

import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.reading.R
import com.example.reading.databinding.FragmentRegisterBinding
import com.example.reading.presentation.view.base.BaseFragment
import com.example.reading.presentation.view.base.visibleOrGone
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

    override fun initializeEvents() {
        binding.edtName.doAfterTextChanged { viewModel.copyName(it.toString()) }
        binding.btnRegister.setOnClickListener { viewModel.saveInfoReader() }

        viewModel.dataLiveData.observe(viewLifecycleOwner) {
            bindViewProgress(it)
            MainFragment.open(findNavController())
        }
    }


    override fun bindView() {
        bindViewProgress(false)
    }

    private fun bindViewProgress(isVisible: Boolean) {
        binding.prgIndicator.visibleOrGone(isVisible)
    }
}