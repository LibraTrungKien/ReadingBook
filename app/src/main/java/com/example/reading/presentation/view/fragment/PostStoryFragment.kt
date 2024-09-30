package com.example.reading.presentation.view.fragment

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.reading.R
import com.example.reading.databinding.FragmentPostStoryBinding
import com.example.reading.presentation.model.Category
import com.example.reading.presentation.view.base.BaseFragment
import com.example.reading.presentation.view.base.apiCall
import com.example.reading.presentation.view.base.visibleOrGone
import com.example.reading.presentation.view.diglog.MessageDialog
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

    override fun initializeEvents() {
        binding.toolbar.setNavigationOnClickListener { findNavController().popBackStack() }

        binding.rdgNew.setOnCheckedChangeListener { _, checkedId ->
            clearData()
            viewModel.isAddStory = checkedId == R.id.btnNewStory
            binding.lytNewChap.visibleOrGone(checkedId == R.id.btnNewChap)
            binding.lytNewStory.visibleOrGone(checkedId != R.id.btnNewChap)
        }

        binding.edtStoryName.doAfterTextChanged {
            viewModel.copyNameStory(it.toString().trim())
        }
        binding.edtChapName.doAfterTextChanged {
            viewModel.copyChapName(it.toString().trim())
        }
        binding.edtDescription.doAfterTextChanged {
            viewModel.copyDescription(it.toString().trim())
        }

        binding.btnUploadChapter.setOnClickListener { requestPermission() }

        binding.btnUpload.setOnClickListener { requestPermission() }
        binding.btnDelete.setOnClickListener { }

        binding.edtContent.doAfterTextChanged { viewModel.copyContent(it.toString().trim()) }
        binding.edtContentV2.doAfterTextChanged { viewModel.copyContent(it.toString().trim()) }
        binding.tilCategory.setEndIconOnClickListener {
            showCategoryPopup(it)
        }
        binding.tilCategory.setErrorIconOnClickListener { showCategoryPopup(it) }

        binding.tilStoryName.setEndIconOnClickListener { showStoryPopup(it) }
        binding.tilStoryName.setErrorIconOnClickListener { showStoryPopup(it) }
        binding.edtChapNameV2.doAfterTextChanged { viewModel.copyChapName(it.toString().trim()) }

        binding.btnSave.setOnClickListener {
            if (viewModel.isAddStory) {
                postStory()
            } else {
                putStory()
            }
        }

        getContentCallback = {
            viewModel.imageUri = it
            bindViewImage(it.toString())
        }
    }

    private fun requestPermission() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            openGallery()
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
                requestPermissionLaunch.launch(Manifest.permission.READ_MEDIA_IMAGES)
            }else{
                requestPermissionLaunch.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
            }
            permissionCallback = {
                if (it) {
                    openGallery()
                }
            }
        }
    }

    private fun clearData() {
        viewModel.story.apply {
            id = 0
            category = -1
            chapters = arrayListOf()
            description = ""
            name = ""
            image = ""
            dateCreated = System.currentTimeMillis()
            dateUpdated = System.currentTimeMillis()
        }

        viewModel.chapter.apply {
            id = ""
            index = 0
            title = ""
            content = ""
        }
        binding.edtChapName.setText("")
        binding.edtChapNameV2.setText("")
        binding.edtContentV2.setText("")
        binding.edtContent.setText("")
        binding.edtDescription.setText("")
        binding.edtStoryName.setText("")
        binding.txtCategory.setText("")
        binding.txtStoryName.setText("")
    }

    private fun showCategoryPopup(anchor: View) {
        CategoryPopup.show(viewModel.categories, requireContext(), anchor) {
            binding.txtCategory.setText(Category.getName(it))
            viewModel.copyCategory(it)
            binding.tilCategory.error = null
            binding.tilCategory.isErrorEnabled = false
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

    private fun bindViewImage(image: String) {
        Glide.with(binding.imgStory).load(image).into(binding.imgStory)
        Glide.with(binding.imgChapter).load(image).into(binding.imgChapter)
    }

    private fun showStoryPopup(anchor: View) {
        StoryPopup.show(viewModel.stories, requireContext(), anchor) {
            viewModel.copyStory(it)
            binding.txtStoryName.setText(it.name)
            binding.tilStoryName.error = null
            binding.tilStoryName.isErrorEnabled = false
        }
    }


    private fun putStory() {
        val story = viewModel.story
        val chapter = viewModel.chapter
        if (story.name.isBlank()) {
            binding.tilStoryName.error = "Vui lòng chọn tên truyện"
            return
        }

        if (chapter.title.isBlank()) {
            binding.edtChapNameV2.error = "Vui lòng nhập tên chap"
            return
        }

        if (chapter.content.isBlank()) {
            binding.edtContentV2.error = "Vui lòng chọn nội dung truyện"
            return
        }

        bindViewProgress(true)
        apiCall(viewModel.putStory(requireActivity()), viewLifecycleOwner, {
            bindViewProgress(false)
            MessageDialog.show(
                parentFragmentManager,
                "Chúc mừng",
                "Bạn đã đăng thành công!",
                R.drawable.satisfied
            ) { findNavController().popBackStack() }
        }, {
            bindViewProgress(false)
            true
        })
    }

    private fun postStory() {
        val story = viewModel.story
        val chapter = viewModel.chapter
        if (story.category == -1) {
            binding.tilCategory.error = "Vui lòng chọn thể loại"
            return
        }

        if (story.name.isBlank()) {
            binding.edtStoryName.error = "Vui lòng nhập tên truyện"
            return
        }

        if (chapter.title.isBlank()) {
            binding.edtChapName.error = "Vui lòng nhập tên chapter"
            return
        }

        if (story.description.isBlank()) {
            binding.edtDescription.error = "Vui lòng nhập mô tả"
            return
        }

        if (chapter.content.isBlank()) {
            binding.edtContent.error = "Vui lòng nhập nội dung chap"
            return
        }

        bindViewProgress(true)
        apiCall(viewModel.postStory(requireContext()), viewLifecycleOwner, {
            bindViewProgress(false)
            MessageDialog.show(
                parentFragmentManager,
                "Chúc mừng",
                "Bạn đã đẩy thành công!",
                R.drawable.satisfied
            ) { findNavController().popBackStack() }
        }, {
            bindViewProgress(false)
            true
        })
    }
}