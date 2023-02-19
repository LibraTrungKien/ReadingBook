package com.example.reading.presentation.view.fragment

import android.view.View
import androidx.core.view.GravityCompat
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.reading.R
import com.example.reading.databinding.FragmentMainBinding
import com.example.reading.presentation.view.adapter.CategoryController
import com.example.reading.presentation.view.adapter.Interactor
import com.example.reading.presentation.view.base.BaseFragment
import com.example.reading.presentation.view.base.apiCall
import com.example.reading.presentation.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : BaseFragment<FragmentMainBinding>() {
    private val viewModel: MainViewModel by viewModels()
    private lateinit var controller: CategoryController

    companion object {
        fun open(navController: NavController) {
            navController.navigate(R.id.actMainFragment)
        }
    }

    override fun createViewBinding() = FragmentMainBinding.inflate(layoutInflater)

    override fun initializeComponent() {
        controller = CategoryController(object : Interactor {
            override fun findNavController(): NavController {
                return this@MainFragment.findNavController()
            }

            override fun getStory() = null
        })
        binding.lstStory.setHasFixedSize(false)
        binding.lstStory.setController(controller)

    }

    override fun initializeEvents() {
        binding.prgIndicator.visibility = View.VISIBLE
        binding.toolbar.setNavigationOnClickListener {
            binding.drwLayout.openDrawer(GravityCompat.START)
        }

        viewModel.dataLiveData.observe(viewLifecycleOwner) {
            controller.setData(it)
            binding.prgIndicator.visibility = View.GONE
        }

        apiCall(viewModel.loadData(), viewLifecycleOwner, {
            controller.setData(it)
        }, { true })
    }

    override fun initializeData() {
        viewModel.initImagesSlider()
    }

    override fun bindView() {
        bindViewImageSlider()
        bindViewImageUser()
        bindViewUserName()
    }

    private fun bindViewImageSlider() {
        binding.imgAuthor.setImageList(viewModel.images)
    }

    private fun bindViewImageUser() {
        Glide.with(binding.crdUser)
            .load("https://www.dungplus.com/wp-content/uploads/2019/12/girl-xinh-1-480x600.jpg")
            .into(binding.crdUser)
    }

    private fun bindViewUserName() {
        binding.txtName.text = "Lee Viet Toan Hoo"
    }
}