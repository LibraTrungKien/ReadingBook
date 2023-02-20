package com.example.reading.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.reading.domain.Repository
import com.example.reading.domain.model.Story
import com.example.reading.presentation.view.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchStoryViewModel @Inject constructor(
    private val repository: Repository
) : BaseViewModel() {
    var textSearch = ""
    var categorySearch = -1
    private val _dataLiveDate: MutableLiveData<List<Story>> = MutableLiveData()
    val dataLiveData: LiveData<List<Story>>
        get() = _dataLiveDate

    fun search() {
        viewModelScope.launch {
            val data = if (textSearch.isBlank()) {
                repository.getHistory()
            } else {
                repository.searchStoryByName(name = textSearch)
            }
            _dataLiveDate.postValue(data)
        }
    }

    fun getHistory() {
        viewModelScope.launch {
            val data = repository.getHistory()
            _dataLiveDate.postValue(data)
        }
    }

    fun searchStoryByCategory() {
        viewModelScope.launch {
            val data = repository.getStoryByCategory(category = categorySearch)
            _dataLiveDate.postValue(data)
        }
    }

    fun addHistory(story: Story) {
        viewModelScope.launch {
            repository.addHistory(story)
        }
    }

}