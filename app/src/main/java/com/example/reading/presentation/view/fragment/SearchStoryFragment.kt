package com.example.reading.presentation.view.fragment

import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.reading.R
import com.example.reading.databinding.FragmentSearchStoryBinding
import com.example.reading.presentation.view.Interactor
import com.example.reading.presentation.view.adapter.StoryController
import com.example.reading.presentation.view.base.BaseFragment
import com.example.reading.presentation.view.base.visibleOrGone
import com.example.reading.presentation.viewmodel.SearchStoryViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchStoryFragment : BaseFragment<FragmentSearchStoryBinding>() {
    private lateinit var controller: StoryController
    private val viewModel: SearchStoryViewModel by viewModels()
    override fun createViewBinding() = FragmentSearchStoryBinding.inflate(layoutInflater)

    companion object {
        fun open(navController: NavController) {
            navController.navigate(R.id.actSearchStoryFragment)
        }
    }

    override fun initializeComponent() {
        controller = StoryController(object : Interactor {
            override fun findNavController() = this@SearchStoryFragment.findNavController()
            override fun isFromSearch() = true
            override fun getStory() = null
            override fun getViewModel() = viewModel
            override fun getFragment() = this@SearchStoryFragment
        })
        binding.lstStory.setHasFixedSize(false)
        binding.lstStory.setController(controller)
    }

    override fun initializeEvents() {
        binding.btnBack.setOnClickListener { findNavController().popBackStack() }

        binding.edtSearch.doAfterTextChanged {
            viewModel.textSearch = it.toString().trim()
            viewModel.categorySearch = -1
            bindViewResult(null)
            binding.lyt1.visibleOrGone(it.isNullOrBlank())
            viewModel.search()
        }

        viewModel.dataLiveData.observe(viewLifecycleOwner) {
            controller.setData(it)
            bindViewResult(it.size)
        }

        binding.btnGhost.setOnClickListener {
            viewModel.categorySearch = 0
            viewModel.searchStoryByCategory()
        }
        binding.btnFairyTales.setOnClickListener {
            viewModel.categorySearch = 1
            viewModel.searchStoryByCategory()
        }
        binding.btnJokes.setOnClickListener {
            viewModel.categorySearch = 2
            viewModel.searchStoryByCategory()
        }
        binding.btnFairyTalesVi.setOnClickListener {
            viewModel.categorySearch = 3
            viewModel.searchStoryByCategory()
        }
        binding.btnFolkTale.setOnClickListener {
            viewModel.categorySearch = 4
            viewModel.searchStoryByCategory()
        }
    }

    override fun initializeData() {
        viewModel.getHistory()
    }

    private fun bindViewResult(result: Int?) {
        val isSearch = viewModel.textSearch.isNotBlank() || viewModel.categorySearch != -1
        binding.txtHistory.text = if (isSearch) {
            "${requireContext().getString(R.string.history)} $result ${requireContext().getString(R.string.result)}"
        } else {
            requireContext().getString(R.string.search_history)
        }
    }

}