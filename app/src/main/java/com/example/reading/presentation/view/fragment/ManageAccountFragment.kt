package com.example.reading.presentation.view.fragment

import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.reading.R
import com.example.reading.databinding.FragmentManageAccountBinding
import com.example.reading.domain.model.Account
import com.example.reading.presentation.view.adapter.AccountController
import com.example.reading.presentation.view.adapter.OnMenuClick
import com.example.reading.presentation.view.base.BaseFragment
import com.example.reading.presentation.view.base.apiCall
import com.example.reading.presentation.view.diglog.ConfirmDialog
import com.example.reading.presentation.viewmodel.ManageAccountViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ManageAccountFragment : BaseFragment<FragmentManageAccountBinding>() {
    private val viewModel: ManageAccountViewModel by viewModels()
    private lateinit var controller: AccountController
    override fun createViewBinding() = FragmentManageAccountBinding.inflate(layoutInflater)

    companion object {
        fun open(navController: NavController) {
            navController.navigate(R.id.actManageAccountFragment)
        }
    }

    override fun initializeComponent() {
        controller = AccountController(object : OnMenuClick {
            override fun onMenuClicked(account: Account) {
                handleDeleteAccount(account)
            }
        })
        binding.lstAccount.setHasFixedSize(false)
        binding.lstAccount.setController(controller)
    }

    override fun initializeEvents() {
        viewModel.dataLiveData.observe(viewLifecycleOwner) {
            controller.setData(it)
        }

        binding.edtSearchAccount.doAfterTextChanged {
            viewModel.textSearch = it.toString().trim()
            viewModel.search()
        }

        binding.btnAddAccount.setOnClickListener { AddAccountFragment.open(findNavController()) }
    }

    override fun initializeData() {
        loadData()
    }

    private fun loadData() {
        apiCall(viewModel.loadData(), viewLifecycleOwner, {
            controller.setData(it)
        }, { true })
    }

    private fun handleDeleteAccount(account: Account) {
        ConfirmDialog.show(
            parentFragmentManager,
            requireContext().getString(R.string.confirm),
            "Bạn có chắn chắn muốn xóa tài khoản ${account.username} ?"
        ) {
            apiCall(viewModel.deleteAccount(account.id), viewLifecycleOwner, {
                loadData()
            }, { true })
        }
    }
}