package com.example.reading.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.reading.domain.Repository
import com.example.reading.domain.model.Story
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostManagementViewModel @Inject constructor(private val appRepository: Repository) :
    ViewModel() {

    private val _dataLiveData: MutableLiveData<List<Story>> = MutableLiveData()
    val dataLiveData: LiveData<List<Story>>
        get() = _dataLiveData

    fun getStoryNotYetApprove() {
        viewModelScope.launch {
            val data = appRepository.getAllStoryNotApprove()
            _dataLiveData.postValue(data)
        }
    }

    fun updateStatusStory(story: Story){
        viewModelScope.launch {
            appRepository.putStory(story)
            getStoryNotYetApprove()
        }
    }
}