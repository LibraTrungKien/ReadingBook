package com.example.reading.presentation.viewmodel

import android.os.Bundle
import com.example.reading.domain.model.Story
import com.example.reading.presentation.Key
import com.example.reading.presentation.view.base.BaseViewModel
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class StoryViewModel @Inject constructor() : BaseViewModel() {
    lateinit var story: Story

    fun initializeArgument(bundle: Bundle) {
        val result = bundle.getString(Key.DATA)
        story = Gson().fromJson(result, Story::class.java)
    }
}