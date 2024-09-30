package com.example.reading.presentation.view.fragment

import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.reading.R
import com.example.reading.databinding.FragmentProfileBinding
import com.example.reading.presentation.view.base.BaseFragment
import com.example.reading.presentation.viewmodel.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>() {
    private val viewModel: ProfileViewModel by viewModels()
    override fun createViewBinding() = FragmentProfileBinding.inflate(layoutInflater)

    companion object {
        fun open(navController: NavController) {
            navController.navigate(R.id.actProfileFragment)
        }
    }

    override fun initializeEvents() {
        binding.toolbar.setOnNavigationClick { findNavController().popBackStack() }
        binding.toolbar.setOnActionClick {
            EditProfileFragment.open(findNavController(), viewModel.account)
        }

        viewModel.dataLiveData.observe(viewLifecycleOwner) { account ->
            binding.txtUserName.text = account.username
            binding.txtGmail.text = account.email
//            binding.txtPermission.text = if (account.permission) "Admin" else "user"

            bindViewImage(account.avatar)
        }
    }

    override fun initializeData() {
        viewModel.loadData()
    }

    private fun bindViewImage(avt: String) {
        Glide.with(binding.imgProfile).load(avt).into(binding.imgProfile)
    }

}