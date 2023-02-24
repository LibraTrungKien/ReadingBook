package com.example.reading.presentation.view.fragment

import android.view.MenuItem
import androidx.core.view.GravityCompat
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.reading.R
import com.example.reading.databinding.FragmentMainBinding
import com.example.reading.presentation.view.adapter.CategoryController
import com.example.reading.presentation.view.Interactor
import com.example.reading.presentation.view.base.BaseFragment
import com.example.reading.presentation.view.base.visibleOrGone
import com.example.reading.presentation.view.diglog.ConfirmDialog
import com.example.reading.presentation.view.diglog.InfoAppDialog
import com.example.reading.presentation.view.diglog.MessageDialog
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
        binding.navReader.setNavigationItemSelectedListener(this)
        binding.navAuthor.setNavigationItemSelectedListener(this)

        viewModel.dataUserLiveData.observe(viewLifecycleOwner) {
            bindViewAccount()
        }
    }

    override fun initializeData() {
        viewModel.loadDataImage()
        viewModel.loadData()
        viewModel.loadDataUser()
    }

    override fun bindView() {
        bindViewImageSlider()
    }

    private fun bindViewImageSlider() {
        binding.imgAuthor.setImageList(viewModel.images)
    }

    private fun bindViewAccount() {
        val isAdmin = viewModel.readerName.isBlank()
        val name: String
        if (isAdmin) {
            val account = viewModel.account
            name = account.username

            Glide.with(binding.crdUser)
                .load(account.avatar)
                .into(binding.crdUser)
        } else {
            name = viewModel.readerName
        }
        binding.txtName.text = name

        if (!viewModel.isFirst())
            return

        MessageDialog.show(
            parentFragmentManager,
            "Xin chào",
            "Chúc mừng $name đến với app của tao",
            R.drawable.satisfied
        ) { viewModel.isFirst++ }

    }

    private fun bindViewProgress(isVisible: Boolean) {
        binding.prgIndicator.visibleOrGone(isVisible)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        binding.drwLayout.closeDrawer(GravityCompat.START)
        when (item.itemId) {
            R.id.favoritePage -> StoryFavouriteFragment.open(findNavController())
            R.id.infoPage -> InfoAppDialog.show(parentFragmentManager)
            R.id.manageStoryPage -> openManageStory()
            R.id.postPage -> openPostStory()
            R.id.logoutPage -> showConfirmDialogLogout()
            else -> Unit
        }
        return true
    }

    private fun showConfirmDialogLogout() {
        ConfirmDialog.show(
            parentFragmentManager,
            requireContext().getString(R.string.confirm),
            requireContext().getString(R.string.are_you_sure)
        ) {
            viewModel.logout()
            StartFragment.open(findNavController())
        }
    }

    private fun openPostStory() {
        if (viewModel.isReader()) {
            MessageDialog.show(
                parentFragmentManager,
                "Thông báo",
                "Bạn cần quyền Admin để sử dụng chức năng này!",
                R.drawable.ic_sad
            ) {}
            return
        }
        PostStoryFragment.open(findNavController())
    }

    private fun openManageStory() {
        if (viewModel.isReader()) {
            MessageDialog.show(
                parentFragmentManager,
                "Thông báo",
                "Bạn cần quyền Admin để sử dụng chức năng này!",
                R.drawable.ic_sad
            ) {}
            return
        }

        ManagerStoryFragment.open(findNavController(), viewModel.account)
    }
}