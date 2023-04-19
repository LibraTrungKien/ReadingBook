package com.example.reading.presentation.viewmodel

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
class BuyStoryViewModel @Inject constructor(private val repository: Repository) : ViewModel() {
    val data = arrayListOf<Story>()
    private val _dataLiveData: MutableLiveData<List<Story>> = MutableLiveData()
    val dataLiveData: LiveData<List<Story>>
        get() = _dataLiveData

    fun getData() {
        data.clear()
        viewModelScope.launch {
            val product = repository.getProductById()
            product.stories.forEach {
                val item = repository.getStoryById(it) ?: return@forEach
                data.add(item)
            }
            _dataLiveData.postValue(data)
        }
    }
}