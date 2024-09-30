package com.example.reading.presentation.view.fragment

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.reading.R
import com.example.reading.databinding.FragmentEditProfileBinding
import com.example.reading.domain.model.Account
import com.example.reading.presentation.Key
import com.example.reading.presentation.view.base.BaseFragment
import com.example.reading.presentation.view.base.apiCall
import com.example.reading.presentation.view.diglog.MessageDialog
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

    override fun initializeEvents() {
        binding.toolbar.setOnNavigationClick { findNavController().popBackStack() }
        binding.toolbar.setOnActionClick { saveAccount() }

        binding.edtUsername.doAfterTextChanged { viewModel.copyUsername(it.toString().trim()) }
        binding.edtGmail.doAfterTextChanged {
            viewModel.copyGmail(
                it.toString().trim()
            )
        }

        binding.btnUploadImage.setOnClickListener { requestPermission() }
        binding.edtPassword.doAfterTextChanged { viewModel.copyPassword(it.toString().trim()) }

        getContentCallback = { uri ->
            viewModel.imageUri = uri
            bindViewImage(uri.toString())
        }
    }

    override fun bindView() {
        val account = viewModel.account
        binding.edtUsername.setText(account.username)
        binding.edtGmail.setText(account.email)
        binding.edtPassword.setText(account.password)

        bindViewImage(account.avatar)
    }

    private fun bindViewImage(avatar: String) {
        val data = avatar.ifBlank { R.drawable.ic_account }
        Glide.with(binding.imgProfile)
            .load(data).into(binding.imgProfile)
    }

    private fun checkPermission() = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.READ_MEDIA_IMAGES
        ) == PackageManager.PERMISSION_GRANTED
    } else {
        ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.READ_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED
    }


    private fun requestPermission() {
        if (checkPermission()) {
            openGallery()
        } else {
            requestPermissionLaunch.launch(if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) Manifest.permission.READ_MEDIA_IMAGES else Manifest.permission.READ_EXTERNAL_STORAGE)
            permissionCallback = {
                if (it) {
                    openGallery()
                }
            }
        }
    }

    private fun saveAccount() {
        apiCall(viewModel.saveAccount(requireContext()), viewLifecycleOwner, {
            MessageDialog.show(
                parentFragmentManager,
                requireContext().getString(R.string.notification),
                requireContext().getString(R.string.content_congratulation),
                R.drawable.satisfied
            ) {
                findNavController().popBackStack()
            }
        }, { true })

    }
}