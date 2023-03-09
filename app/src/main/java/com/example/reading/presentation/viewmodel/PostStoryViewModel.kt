package com.example.reading.presentation.viewmodel

import FileUtils
import android.content.Context
import android.net.Uri
import androidx.lifecycle.viewModelScope
import com.example.reading.domain.Repository
import com.example.reading.domain.model.Chapter
import com.example.reading.domain.model.Story
import com.example.reading.presentation.view.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class PostStoryViewModel @Inject constructor(
    private val repository: Repository
) : BaseViewModel() {
    var story: Story = Story()
    var chapter: Chapter = Chapter()
    var stories = listOf<Story>()
    var imageUri: Uri? = null
    private val sdf = SimpleDateFormat("dd/MM/yyyy", Locale("VINA"))
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
            val authorName = repository.getInfoAccount().username
            stories = repository.getStoryByAuthor(authorName)
        }
    }

    fun copyStory(value: Story) {
        story.apply {
            id = value.id
            name = value.name
            description = value.description
            dateCreated = value.dateCreated
            dateUpdated = value.dateUpdated
            chapters = value.chapters
            category = value.category
            image = value.image
            author = value.author
            status = value.status
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
            if (!isMatch()) {
                chapters.add(chapter)
            }
        }
        repository.putStory(story)
    }

    private fun getChapIndex() = story.chapters.last().index

    private suspend fun uploadImage(context: Context): String {
        if (imageUri == null) return ""
        val path = FileUtils.getRealPath(context, imageUri!!)
        val file = File(path!!)
        val requestBody = file.asRequestBody("multipart/form-data".toMediaType())
        val multipleBody = MultipartBody.Part.createFormData("file", file.name, requestBody)
        return repository.uploadImage(multipleBody)
    }

    fun postStory(context: Context) = callSafeApiWithLiveData {
        val imageUri = uploadImage(context)
        chapter.apply {
            id = "1"
            index = 1
        }
        story.apply {
            image = imageUri
            dateUpdated = sdf.format(now)
            dateCreated = sdf.format(now)
            if (!isMatch()) {
                chapters.add(chapter)
            }
        }
        repository.postStory(story)
    }

    private fun isMatch(): Boolean {
        val chapId = chapter.id
        val chapters = story.chapters
        return chapters.any { it.id == chapId }
    }

}