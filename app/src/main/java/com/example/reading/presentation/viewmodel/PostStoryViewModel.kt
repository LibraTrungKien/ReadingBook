package com.example.reading.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.example.reading.domain.Repository
import com.example.reading.domain.model.Chapter
import com.example.reading.domain.model.Story
import com.example.reading.presentation.view.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostStoryViewModel @Inject constructor(
    private val repository: Repository
) : BaseViewModel() {
    lateinit var story: Story
    val chapter: Chapter = Chapter()
    var stories = listOf<Story>()
    val categories = listOf(
        "Truyện ma",
        "Truyện cổ tích thế giới",
        "Truyện cười",
        "Truyện cổ tích Việt Nam",
        "Truyện dân gian"
    )
    var isAddStory: Boolean = false

    fun loadStoryByAuthor() {
        viewModelScope.launch {
            stories = repository.getStoryByAuthor("Trần Đan")
        }
    }

    fun copyCategory(value: Int) {
        story.category = value
    }

    fun copyNameStory(value: String) {
        story.name = value
    }

    fun copyDescription(value: String) {
        story.description = value
    }

    fun copyImageLink(value: String) {
        story.image = value
    }

    fun copyChapName(value: String) {
        chapter.title = value
    }

    fun copyContent(value: String) {
        chapter.content = value
    }

    fun putStory() = callSafeApiWithLiveData {
        val index = getChapIndex() + 1
        chapter.apply {
            this.id = index.toString()
            this.index = index
        }
        story.chapters.add(chapter)
        repository.putStory(story)
    }

    private fun getChapIndex() = story.chapters.last().index

    fun postStory() = callSafeApiWithLiveData {

        repository.postStory(story)
    }

}