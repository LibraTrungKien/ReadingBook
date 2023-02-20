package com.example.reading.presentation.viewmodel

import com.example.reading.domain.Repository
import com.example.reading.presentation.view.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PostStoryViewModel @Inject constructor(
    private val repository: Repository
) : BaseViewModel() {
}