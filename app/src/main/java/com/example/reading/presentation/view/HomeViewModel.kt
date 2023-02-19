package com.example.reading.presentation.view

import com.example.reading.domain.Repository
import com.example.reading.presentation.view.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: Repository
) : BaseViewModel() {
    fun initializeData() = callSafeApiWithLiveData {
        repository.fetchAllStory()
    }
}