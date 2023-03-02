package com.example.reading.presentation.view.fragment

import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.reading.R
import com.example.reading.databinding.FragmentManageAccountBinding
import com.example.reading.presentation.view.adapter.AccountController
import com.example.reading.presentation.view.base.BaseFragment
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
        controller = AccountController()
        binding.lstAccount.setHasFixedSize(false)
        binding.lstAccount.setController(controller)
    }

    override fun initializeEvents() {
        viewModel.dataLiveData.observe(viewLifecycleOwner) {
            controller.setData(it)
        }

        binding.btnAddAccount.setOnClickListener { AddAccountFragment.open(findNavController()) }
    }

    override fun initializeData() {
        viewModel.loadData()
    }
}