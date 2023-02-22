package com.example.reading.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.reading.domain.Repository
import com.example.reading.domain.model.Chapter
import com.example.reading.domain.model.Story
import com.example.reading.presentation.view.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.TimeZone
import javax.inject.Inject

@HiltViewModel
class PostStoryViewModel @Inject constructor(
    private val repository: Repository
) : BaseViewModel() {
    var story: Story = Story()
    var chapter: Chapter = Chapter()
    var stories = listOf<Story>()
    private val sdf = SimpleDateFormat("dd/MM/yyyy hh:mm:ss aa", Locale("VINA")).apply {
        timeZone = TimeZone.getDefault()
    }
    private val now = Calendar.getInstance().time

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
        story.apply {
            dateUpdated = sdf.format(now)
            chapters.add(chapter)
        }
        repository.putStory(story)
    }

    private fun getChapIndex() = story.chapters.last().index

    fun postStory() = callSafeApiWithLiveData {
        chapter.apply {
            id = "0"
        }
        story.apply {
            dateUpdated = sdf.format(now)
            dateCreated = sdf.format(now)
            chapters.add(chapter)
        }
        repository.postStory(story)
    }

}