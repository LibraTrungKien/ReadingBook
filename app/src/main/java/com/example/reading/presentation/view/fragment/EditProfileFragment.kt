package com.example.reading.presentation.view.fragment

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
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

    private val getContent =
        registerForActivityResult(ActivityResultContracts.GetContent()) {
            it ?: return@registerForActivityResult
            getContentCallback(it)
        }
    private var getContentCallback: (uri: Uri) -> Unit = {}

    private val requestPermissionLaunch =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            permissionCallback(it)
        }
    private var permissionCallback: (isGranted: Boolean) -> Unit = {}

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
        binding.edtPhoneNumber.doAfterTextChanged {
            viewModel.copyPhoneNumber(
                it.toString().trim()
            )
        }

        binding.btnUploadImage.setOnClickListener { requestPermission() }
        binding.edtPassword.doAfterTextChanged { viewModel.copyPassword(it.toString().trim()) }
        binding.rdgGender.setOnCheckedChangeListener { _, id ->
            val gender = if (id == R.id.rdbMale) "nam" else "nữ"
            viewModel.copyGender(gender)
        }

        getContentCallback = { uri ->
            viewModel.imageUri = uri
            bindViewImage(uri.toString())
        }
    }

    override fun bindView() {
        val account = viewModel.account
        binding.edtUsername.setText(account.username)
        binding.edtPhoneNumber.setText(account.phone)
        binding.edtPassword.setText(account.password)

        if (account.gender == "nam") {
            binding.rdbMale.isChecked = true
        } else {
            binding.rdbFeMale.isChecked = true
        }

        bindViewImage(account.avatar)
    }

    private fun bindViewImage(avatar: String) {
        val data = avatar.ifBlank { R.drawable.ic_account }
        Glide.with(binding.imgProfile)
            .load(data).into(binding.imgProfile)
    }

    private fun requestPermission() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            openGallery()
        } else {
            requestPermissionLaunch.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
            permissionCallback = {
                if (it) {
                    openGallery()
                }
            }
        }
    }

    private fun openGallery() {
        val input = "image/*"
        getContent.launch(input)
    }

    private fun saveAccount() {
        apiCall(viewModel.saveAccount(requireContext()), viewLifecycleOwner, {
            MessageDialog.show(
                parentFragmentManager,
                requireContext().getString(R.string.notification),
                requireContext().getString(R.string.content_congratulation),
                R.drawable.satisfied
            ) {}
        }, { true })

    }
}