package com.example.reading.presentation.view.fragment

import android.view.View
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.reading.R
import com.example.reading.databinding.FragmentPostStoryBinding
import com.example.reading.domain.model.Story
import com.example.reading.presentation.model.Category
import com.example.reading.presentation.view.MessageDialog
import com.example.reading.presentation.view.base.BaseFragment
import com.example.reading.presentation.view.base.apiCall
import com.example.reading.presentation.view.base.visibleOrGone
import com.example.reading.presentation.view.popup.CategoryPopup
import com.example.reading.presentation.view.popup.StoryPopup
import com.example.reading.presentation.viewmodel.PostStoryViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PostStoryFragment : BaseFragment<FragmentPostStoryBinding>() {
    private val viewModel: PostStoryViewModel by viewModels()
    override fun createViewBinding() = FragmentPostStoryBinding.inflate(layoutInflater)

    companion object {
        fun open(navController: NavController) {
            navController.navigate(R.id.actPostStoryFragment)
        }
    }

    override fun initializeComponent() {

    }

    override fun initializeEvents() {
        binding.toolbar.setNavigationOnClickListener { findNavController().popBackStack() }

        binding.rdgNew.setOnCheckedChangeListener { _, checkedId ->
            clearData()
            viewModel.isAddStory = checkedId == R.id.btnNewStory
            binding.lytNewChap.visibleOrGone(checkedId == R.id.btnNewChap)
            binding.lytNewStory.visibleOrGone(checkedId != R.id.btnNewChap)
        }

        binding.tilCategory.setEndIconOnClickListener {
            showCategoryPopup(it)
        }

        binding.tilStoryName.setEndIconOnClickListener { showStoryPopup(it) }
        binding.edtChapNameV2.doAfterTextChanged { viewModel.copyChapName(it.toString()) }
        binding.edtContentV2.doAfterTextChanged { viewModel.copyContent(it.toString()) }

        binding.btnSave.setOnClickListener {
            if (viewModel.isAddStory) {
                postStory()
            } else {
                putStory()
            }
        }
    }

    private fun clearData() {
        binding.edtChapName.setText("")
        binding.edtChapNameV2.setText("")
        binding.edtContentV2.setText("")
        binding.edtContent.setText("")
        binding.edtDescription.setText("")
        binding.edtStoryName.setText("")
        binding.edtImage.setText("")
        binding.txtCategory.setText("")
        binding.txtStoryName.setText("")
        viewModel.story = Story()
    }

    private fun showCategoryPopup(anchor: View) {
        CategoryPopup.show(viewModel.categories, requireContext(), anchor) {
            binding.txtCategory.setText(Category.getName(it))
            viewModel.copyCategory(it)
        }
    }

    override fun initializeData() {
        viewModel.loadStoryByAuthor()
    }

    override fun bindView() {
        bindViewProgress(false)
    }

    private fun bindViewProgress(isVisible: Boolean) {
        binding.prgIndicator.visibleOrGone(isVisible)
    }

    private fun showStoryPopup(anchor: View) {
        StoryPopup.show(viewModel.stories, requireContext(), anchor) {
            handleWhenClicked(it)
        }
    }

    private fun handleWhenClicked(story: Story) {
        viewModel.story = story
        binding.txtStoryName.setText(story.name)
    }

    private fun putStory() {
        bindViewProgress(true)
        apiCall(viewModel.putStory(), viewLifecycleOwner, {
            bindViewProgress(false)
            MessageDialog.show(
                parentFragmentManager,
                "Chúc mừng",
                "Bạn đã đẩy thành công!",
                R.drawable.satisfied
            ) {}
        }, {
            true
        })
    }

    private fun postStory() {
        bindViewProgress(true)
        apiCall(viewModel.postStory(), viewLifecycleOwner, {
            bindViewProgress(false)
            MessageDialog.show(
                parentFragmentManager,
                "Chúc mừng",
                "Bạn đã đẩy thành công!",
                R.drawable.satisfied
            ) {}
        }, {
            true
        })
    }
}