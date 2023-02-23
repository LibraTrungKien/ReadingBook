package com.example.reading.presentation.view.fragment

import android.view.MenuItem
import androidx.core.view.GravityCompat
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.reading.R
import com.example.reading.databinding.FragmentMainBinding
import com.example.reading.domain.model.Account
import com.example.reading.presentation.view.adapter.CategoryController
import com.example.reading.presentation.view.adapter.Interactor
import com.example.reading.presentation.view.base.BaseFragment
import com.example.reading.presentation.view.base.visibleOrGone
import com.example.reading.presentation.viewmodel.MainViewModel
import com.google.android.material.navigation.NavigationView.OnNavigationItemSelectedListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : BaseFragment<FragmentMainBinding>(), OnNavigationItemSelectedListener {
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
            override fun findNavController() = this@MainFragment.findNavController()
            override fun getStory() = null
            override fun getViewModel() = viewModel
            override fun isFromSearch() = false
            override fun getFragment() = this@MainFragment
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

        binding.btnSearch.setOnClickListener { SearchStoryFragment.open(findNavController()) }
        binding.navLeftMenu.setNavigationItemSelectedListener(this)
    }

    override fun initializeData() {
        viewModel.loadDataImage()
        viewModel.loadData()
        viewModel.loadDataUser()
    }

    override fun bindView() {
        bindViewImageSlider()
        bindViewAccount(viewModel.account)
    }

    private fun bindViewImageSlider() {
        binding.imgAuthor.setImageList(viewModel.images)
    }

    private fun bindViewAccount(account: Account) {
        binding.txtName.text = account.username
        Glide.with(binding.crdUser)
            .load(account.avatar)
            .into(binding.crdUser)
    }

    private fun bindViewProgress(isVisible: Boolean) {
        binding.prgIndicator.visibleOrGone(isVisible)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        binding.drwLayout.closeDrawer(GravityCompat.START)
        when (item.itemId) {
            R.id.postPage -> PostStoryFragment.open(findNavController())
            R.id.favoritePage -> StoryFavouriteFragment.open(findNavController())
            else -> Unit
        }
        return true
    }
}