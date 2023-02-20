package com.example.reading.presentation.view.fragment

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
import com.example.reading.presentation.view.base.visibleOrGone
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
        binding.toolbar.setNavigationOnClickListener {
            binding.drwLayout.openDrawer(GravityCompat.START)
        }

        bindViewProgress(true)
        viewModel.dataLiveData.observe(viewLifecycleOwner) {
            controller.setData(it)
            bindViewProgress(false)
        }
    }

    override fun initializeData() {
        viewModel.loadDataImage()
        viewModel.loadData()
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

    private fun bindViewProgress(isVisible: Boolean) {
        binding.prgIndicator.visibleOrGone(isVisible)
    }
}