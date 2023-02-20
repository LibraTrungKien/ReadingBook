package com.example.reading.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.reading.domain.Repository
import com.example.reading.domain.model.Story
import com.example.reading.presentation.view.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StoryFavouriteViewModel @Inject constructor(
    private val repository: Repository
) : BaseViewModel() {
    private val _dataLiveData: MutableLiveData<List<Story>> = MutableLiveData()
    val dataLiveData: LiveData<List<Story>>
        get() = _dataLiveData

    fun loadData() {
        viewModelScope.launch {
            repository.getStoryFavourites().collectLatest { _dataLiveData.postValue(it) }
        }
    }


    fun deleteStoryFavourite(story: Story) {
        viewModelScope.launch {
            repository.deleteStoryFavourite(story)
        }
    }

}