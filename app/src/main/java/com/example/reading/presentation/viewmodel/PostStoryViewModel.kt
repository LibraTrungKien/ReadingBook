package com.example.reading.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.example.reading.domain.Repository
import com.example.reading.domain.model.Story
import com.example.reading.presentation.view.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostStoryViewModel @Inject constructor(
    private val repository: Repository
) : BaseViewModel() {
    var stories = listOf<Story>()
    fun loadStoryByAuthor(){
        viewModelScope.launch {
            stories = repository.getStoryByAuthor("Trần Đan")
        }
    }
}