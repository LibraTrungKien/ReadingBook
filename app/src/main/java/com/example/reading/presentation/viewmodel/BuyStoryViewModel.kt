package com.example.reading.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.reading.domain.Repository
import com.example.reading.domain.model.Story
import com.example.reading.presentation.view.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BuyStoryViewModel @Inject constructor(private val repository: Repository) : BaseViewModel() {
    val data = arrayListOf<Story>()
    private val _dataLiveData: MutableLiveData<List<Story>> = MutableLiveData()
    val dataLiveData: LiveData<List<Story>>
        get() = _dataLiveData

    fun getData() = callSafeApiWithLiveData {
        data.clear()
        val product = repository.getProductById()
        product.stories.forEach {
            val item = repository.getStoryById(it) ?: return@forEach
            data.add(item)
        }
        _dataLiveData.postValue(data)
    }
}