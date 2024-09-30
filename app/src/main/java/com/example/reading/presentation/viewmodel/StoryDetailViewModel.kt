package com.example.reading.presentation.viewmodel

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.reading.domain.model.Chapter
import com.example.reading.domain.model.Story
import com.example.reading.presentation.Key
import com.example.reading.presentation.view.base.BaseViewModel
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class StoryDetailViewModel @Inject constructor() : BaseViewModel() {
    lateinit var story: Story
    private var currentIndex = 1
    lateinit var currentChap: Chapter

    private var _dataLiveData: MutableLiveData<Chapter> = MutableLiveData()
    val dataLiveData: LiveData<Chapter>
        get() = _dataLiveData

    fun initializeArguments(bundle: Bundle) {
        val result = bundle.getString(Key.DATA)
        story = Gson().fromJson(result, Story::class.java)
        currentIndex = bundle.getInt(Key.INDEX, 1) - 1
        currentChap = story.chapters[currentIndex]
        _dataLiveData.postValue(currentChap)
    }

    fun nextChap() {
        currentIndex++
        _dataLiveData.postValue(story.chapters[currentIndex])
    }

    fun previousChap() {
        currentIndex--
        _dataLiveData.postValue(story.chapters[currentIndex])
    }

    fun isEndChap() = currentIndex == story.chapters.size - 1
    fun isFirstChap() = currentIndex == 0
}