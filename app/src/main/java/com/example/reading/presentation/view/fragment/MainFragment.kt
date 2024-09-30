package com.example.reading.presentation.view.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.view.MenuItem
import androidx.activity.OnBackPressedCallback
import androidx.core.view.GravityCompat
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.denzcoskun.imageslider.interfaces.ItemClickListener
import com.example.reading.R
import com.example.reading.databinding.FragmentMainBinding
import com.example.reading.presentation.view.Interactor
import com.example.reading.presentation.view.RechargeActivity
import com.example.reading.presentation.view.adapter.CategoryController
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
        requireActivity().onBackPressedDispatcher
            .addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    ConfirmDialog.show(
                        parentFragmentManager,
                        requireContext().getString(R.string.confirm),
                        requireContext().getString(R.string.are_you_sure)
                    ) { requireActivity().finish() }
                }
            })

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

        binding.lytProfile.setOnClickListener { ProfileFragment.open(findNavController()) }
    }

    override fun initializeData() {
        viewModel.loadAd()
        viewModel.loadData()
        viewModel.loadDataUser()
    }

    override fun bindView() {
        bindViewImageSlider()
    }

    private fun bindViewImageSlider() {
        binding.imgAuthor.setImageList(viewModel.images)
        binding.imgAuthor.setItemClickListener(object : ItemClickListener {
            override fun onItemSelected(position: Int) {
                when (position) {
                    0 -> openGoogleSearch("https://play.google.com/store/apps/details?id=com.sihamabdulrahman.quizlogodetectiveconan&hl=vi&gl=US")
                    1 -> openGoogleSearch("https://play.google.com/store/apps/details?id=com.bandainamcoent.dblegends_ww&hl=vi&gl=US")
                    2 -> openGoogleSearch("https://play.google.com/store/apps/details?id=com.namcobandaigames.spmoja010E&hl=vi&gl=US")
                }
            }
        })
    }

    private fun bindViewImageUser(image: String) {
        val src = image.ifBlank { R.drawable.ic_account }
        Glide.with(requireActivity())
            .load(src)
            .into(binding.crdUser)
    }

    @SuppressLint("SetTextI18n")
    private fun bindViewAccount() {
        val account = viewModel.account
        bindViewImageUser(account.avatar)
        binding.txtName.text = account.username

        if (!viewModel.isFirst())
            return

        MessageDialog.show(
            parentFragmentManager,
            requireContext().getString(R.string.hello),
            "${requireContext().getString(R.string.congratulation)} ${account.username} ${
                requireContext().getString(
                    R.string.goToStoryWorld
                )
            }",
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
            R.id.myStore -> openManageStory()
            R.id.postPage -> openPostStory()
            R.id.manageAccountPage -> openManageAccountFragment()
            R.id.manageStoryPage -> manageStory()
            R.id.logoutPage -> showConfirmDialogLogout()
//            R.id.rechargePage -> rechargeFragment()
//            R.id.buyStoryPage -> openBuyStory()
            else -> Unit
        }
        return true
    }

    private fun manageStory(){
        PostManagementFragment.open(findNavController())
    }

    private fun showConfirmDialogLogout() {
        ConfirmDialog.show(
            parentFragmentManager,
            requireContext().getString(R.string.confirm),
            requireContext().getString(R.string.are_you_sure)
        ) {
            viewModel.logout()
            LoginFragment.open(findNavController())
        }
    }

    private fun openPostStory() {
        PostStoryFragment.open(findNavController())
    }

    private fun openManageAccountFragment() {
        if (!viewModel.isAdmin()) {
            MessageDialog.show(
                parentFragmentManager,
                requireContext().getString(R.string.notification),
                requireContext().getString(R.string.youNeedAdminPermission),
                R.drawable.ic_sad
            ) {}
            return
        }
        ManageAccountFragment.open(findNavController())
    }

    private fun openManageStory() {
        ManagerStoryFragment.open(findNavController(), viewModel.account)
    }
}