package com.example.reading.presentation.view.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.reading.R
import com.example.reading.databinding.FragmentStoryBinding
import com.example.reading.domain.model.Comment
import com.example.reading.domain.model.Story
import com.example.reading.presentation.Key
import com.example.reading.presentation.Utils
import com.example.reading.presentation.view.Interactor
import com.example.reading.presentation.view.adapter.ChapterController
import com.example.reading.presentation.view.adapter.CommentAdapter
import com.example.reading.presentation.view.base.BaseFragment
import com.example.reading.presentation.view.base.apiCall
import com.example.reading.presentation.viewmodel.StoryViewModel
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class StoryFragment : BaseFragment<FragmentStoryBinding>() {
    private val viewModel: StoryViewModel by viewModels()
    private val commentAdapter: CommentAdapter = CommentAdapter()
    private lateinit var controller: ChapterController

    private var indexStar = 4
    private var isSelected = false

    companion object {
        fun open(navController: NavController, story: Story) {
            val bundle = bundleOf(Key.DATA to Gson().toJson(story))
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

        binding.lstComment.setHasFixedSize(false)
        binding.lstComment.adapter = commentAdapter
    }

    override fun initializeEvents() {
        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        binding.btnReadStory.setOnClickListener {
            openStoryDetail()
        }

        binding.btnAddFavourite.setOnClickListener {
            viewModel.addFavourite()
            Toast.makeText(
                requireContext(),
                "Đã thêm ${viewModel.story.name} vào ds yêu thích",
                Toast.LENGTH_LONG
            ).show()
        }

        binding.btnComment.setOnClickListener {
            viewModel.addComment(binding.edtComment.text.toString())
            binding.edtComment.text.clear()
        }

        viewModel.commentLiveData.observe(this){
            commentAdapter.data = it
        }

        viewModel.rateLiveData.observe(this){
            binding.txtRate.text = it.toString()
        }

        binding.btnRate.setOnClickListener {
            viewModel.rateStory(indexStar)
        }

        viewModel.isRatedLiveData.observe(this){
            CoroutineScope(Dispatchers.Main).launch {

                binding.linStar.isVisible = !it
                binding.btnRate.isVisible = !it
            }
        }

        val listStars = arrayOf(
            binding.imgStar1,
            binding.imgStar2,
            binding.imgStar3,
            binding.imgStar4,
            binding.img5Star,
        )

        viewModel.authorLiveData.observe(this){
            binding.txtAuthor.text = it.username
        }

        listStars.forEachIndexed{ index, view ->
            view.setOnClickListener {
                if (indexStar == index && isSelected) {
                    return@setOnClickListener
                }
                indexStar = index
                isSelected = true

                checkStarRate(index)

                val isSelected = BooleanArray(listStars.size)
                if (!isSelected[index]) {
                    if (view.scaleX == 1f && view.scaleY == 1f) {
                        view.startAnimation(
                            AnimationUtils.loadAnimation(
                                requireContext(),
                                R.anim.zoom_out
                            )
                        )
                    } else {
                        view.startAnimation(
                            AnimationUtils.loadAnimation(
                                requireContext(),
                                R.anim.zoom_in
                            )
                        )
                    }
                }
            }
        }
    }

    private fun checkStarRate(index: Int){
        val starImages = listOf(
            R.drawable.star_fill,
            R.drawable.star_none
        )

        val starImageViews = listOf(
            binding.imgStar1,
            binding.imgStar2,
            binding.imgStar3,
            binding.imgStar4,
            binding.img5Star
        )

        for (i in 0..4) {
            starImageViews[i].setImageResource(starImages[if (i <= index) 0 else 1])
        }
    }

    override fun initializeData() {
        viewModel.getAccount()
        viewModel.getCommentById()
        viewModel.getInfoAuthor()
        viewModel.getRateByStory()
        viewModel.isRated()
    }

    @SuppressLint("SetTextI18n")
    override fun bindView() {
        val story = viewModel.story
        Glide.with(binding.imgStory).load(story.image).into(binding.imgStory)
        binding.txtAuthor.text = story.author_id.toString()
        binding.toolbar.title = story.name
        binding.txtCreatedDate.text = Utils.convertToTime(story.dateCreated)
        binding.txtDescription.text = story.description
        binding.txtCategory.text = story.getCategory()
        binding.txtChapter.text = "${story.chapters.size}"
    }

    private fun openStoryDetail() {
        StoryDetailFragment.open(findNavController(), viewModel.story, 1)
    }

}