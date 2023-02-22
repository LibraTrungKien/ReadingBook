package com.example.reading.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.reading.domain.Repository
import com.example.reading.domain.model.Chapter
import com.example.reading.domain.model.Story
import com.example.reading.presentation.view.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostStoryViewModel @Inject constructor(
    private val repository: Repository
) : BaseViewModel() {
    lateinit var story: Story
    val chapter: Chapter = Chapter()
    var stories = listOf<Story>()

    fun loadStoryByAuthor() {
        viewModelScope.launch {
            stories = repository.getStoryByAuthor("Trần Đan")
        }
    }

    fun copyChapName(value: String) {
        chapter.title = value
    }

    fun copyContent(value: String) {
        chapter.content = value
    }

    fun postStory() = callSafeApiWithLiveData {
        val index = getChapIndex() + 1
        chapter.apply {
            this.id = index.toString()
            this.index = index
        }
        story.chapters.add(chapter)
        repository.putStory(story)
    }

    private fun getChapIndex() = story.chapters.last().index

}