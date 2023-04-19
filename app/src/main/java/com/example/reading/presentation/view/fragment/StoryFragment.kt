package com.example.reading.presentation.view.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.reading.R
import com.example.reading.databinding.FragmentStoryBinding
import com.example.reading.domain.model.Story
import com.example.reading.presentation.Key
import com.example.reading.presentation.view.Interactor
import com.example.reading.presentation.view.adapter.ChapterController
import com.example.reading.presentation.view.base.BaseFragment
import com.example.reading.presentation.view.base.apiCall
import com.example.reading.presentation.view.base.visibleOrGone
import com.example.reading.presentation.view.diglog.ConfirmDialog
import com.example.reading.presentation.view.diglog.MessageDialog
import com.example.reading.presentation.viewmodel.StoryViewModel
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StoryFragment : BaseFragment<FragmentStoryBinding>() {
    private val viewModel: StoryViewModel by viewModels()
    private lateinit var controller: ChapterController

    companion object {
        fun open(navController: NavController, story: Story, readable: Boolean) {
            val bundle = bundleOf(Key.DATA to Gson().toJson(story), Key.READ_ABLE to readable)
            navController.navigate(R.id.actStoryFragment, bundle)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.initializeArgument(requireArguments())
    }

    override fun createViewBinding() = FragmentStoryBinding.inflate(layoutInflater)

    override fun initializeComponent() {
        controller = ChapterController(object : Interactor {
            override fun findNavController() = this@StoryFragment.findNavController()
            override fun isFromSearch() = false
            override fun getStory() = viewModel.story
            override fun getViewModel() = viewModel
            override fun getFragment() = this@StoryFragment
        })

        binding.lstChapter.setHasFixedSize(false)
        binding.lstChapter.setController(controller)
        controller.setData(viewModel.story.chapters)
    }

    override fun initializeEvents() {
        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        binding.btnBuyStory.setOnClickListener {
            if (viewModel.readable) {
                openStoryDetail()
            } else {
                handleBuyStory()
            }
        }

        binding.btnAddFavourite.setOnClickListener {
            viewModel.addFavourite()
            Toast.makeText(
                requireContext(),
                "Đã thêm ${viewModel.story.name} vào ds yêu thích",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    override fun initializeData() {
        viewModel.getAccount()
        apiCall(viewModel.getProduct(), viewLifecycleOwner, {}, { true })
    }

    @SuppressLint("SetTextI18n")
    override fun bindView() {
        val story = viewModel.story
        Glide.with(binding.imgStory).load(story.image).into(binding.imgStory)
        binding.txtAuthor.text = story.author
        binding.toolbar.title = story.name
        binding.txtCreatedDate.text = story.dateCreated
        binding.txtDateUpdated.text = story.dateUpdated
        binding.txtDescription.text = story.description
        binding.txtStatus.text = story.status
        binding.txtCategory.text = story.getCategory()
        binding.txtChapter.text = "${story.chapters.size}"
        binding.txtCast.text = "${story.cost} ${getString(R.string.cast)}"

        val text = if (viewModel.readable) {
            getString(R.string.read_story)
        } else {
            getString(R.string.buy_story)
        }

        binding.lstChapter.visibleOrGone(viewModel.readable)

        binding.btnBuyStory.text = text
    }

    private fun openStoryDetail() {
        StoryDetailFragment.open(findNavController(), viewModel.story, 1)
    }

    private fun handleBuyStory() {
        val story = viewModel.story
        val account = viewModel.account

        if (viewModel.isExist()) {
            MessageDialog.show(
                childFragmentManager,
                getString(R.string.notification),
                getString(R.string.your_story),
                R.drawable.ic_sad
            ) {}
            return
        }

        if (account.cost < story.cost) {
            MessageDialog.show(
                childFragmentManager,
                getString(R.string.notification),
                getString(R.string.cost_not_enough),
                R.drawable.ic_sad
            ) {}
            return
        }

        ConfirmDialog.show(
            childFragmentManager,
            getString(R.string.confirm),
            getString(R.string.confirm_BuyStory) + " " + story.name + " " + story.cost
        ) {
            apiCall(viewModel.updateAccount(), viewLifecycleOwner, {
                updateProduct()
            }, { true })
        }
    }

    private fun updateProduct() {
        apiCall(viewModel.updateProduct(), viewLifecycleOwner, {
            Toast.makeText(requireContext(), getString(R.string.success), Toast.LENGTH_SHORT).show()
        }, { true })
    }

}