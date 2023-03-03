package com.example.reading.presentation.view.fragment

import android.os.Bundle
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.reading.R
import com.example.reading.databinding.FragmentProfileBinding
import com.example.reading.domain.model.Account
import com.example.reading.presentation.Key
import com.example.reading.presentation.view.base.BaseFragment
import com.example.reading.presentation.viewmodel.ProfileViewModel
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>() {
    private val viewModel: ProfileViewModel by viewModels()
    override fun createViewBinding() = FragmentProfileBinding.inflate(layoutInflater)

    companion object {
        fun open(navController: NavController, account: Account) {
            val bundle = bundleOf(Key.DATA to Gson().toJson(account))
            navController.navigate(R.id.actProfileFragment, bundle)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.initializeArgument(requireArguments())
    }

    override fun initializeEvents() {
        binding.toolbar.setOnNavigationClick { findNavController().popBackStack() }
        binding.toolbar.setOnActionClick {
            Toast.makeText(
                requireContext(),
                "setting...",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun bindView() {
        val account = viewModel.account
        binding.txtUserName.text = account.username
        binding.txtPhoneNumber.text = account.phone
        binding.txtPermission.text = account.permission
        binding.txtGender.text = account.gender
    }
}