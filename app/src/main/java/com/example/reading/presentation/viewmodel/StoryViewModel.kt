package com.example.reading.presentation.viewmodel

import android.os.Bundle
import androidx.lifecycle.viewModelScope
import com.example.reading.domain.Repository
import com.example.reading.domain.model.Story
import com.example.reading.presentation.Key
import com.example.reading.presentation.view.base.BaseViewModel
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StoryViewModel @Inject constructor(
    private val repository: Repository
) : BaseViewModel() {
    lateinit var story: Story

    fun initializeArgument(bundle: Bundle) {
        val result = bundle.getString(Key.DATA)
        story = Gson().fromJson(result, Story::class.java)
    }

    fun addFavourite() {
        viewModelScope.launch { repository.addFavourite(story) }
    }
}