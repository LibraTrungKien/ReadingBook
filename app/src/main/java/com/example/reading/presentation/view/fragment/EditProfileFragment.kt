package com.example.reading.presentation.view.fragment

import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import com.example.reading.R
import com.example.reading.databinding.FragmentEditProfileBinding
import com.example.reading.domain.model.Account
import com.example.reading.presentation.Key
import com.example.reading.presentation.view.base.BaseFragment
import com.example.reading.presentation.viewmodel.EditProfileViewModel
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditProfileFragment : BaseFragment<FragmentEditProfileBinding>() {
    private val viewModel: EditProfileViewModel by viewModels()
    override fun createViewBinding() = FragmentEditProfileBinding.inflate(layoutInflater)

    companion object {
        fun open(navController: NavController, account: Account) {
            val bundle = bundleOf(Key.DATA to Gson().toJson(account))
            navController.navigate(R.id.actEditProfileFragment, bundle)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.initializeArgument(requireArguments())
    }

    override fun bindView() {
        val account = viewModel.account
        binding.txtUserName.setText(account.username)
        binding.txtPhoneNumber.setText(account.phone)
        if (account.gender == "nam") {
            binding.rdbMale.isChecked = true
        } else {
            binding.rdbFeMale.isChecked = true
        }
    }
}