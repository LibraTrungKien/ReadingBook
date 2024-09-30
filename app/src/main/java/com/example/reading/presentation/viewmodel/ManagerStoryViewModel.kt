package com.example.reading.presentation.viewmodel

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.reading.domain.Repository
import com.example.reading.domain.model.Account
import com.example.reading.domain.model.Story
import com.example.reading.presentation.Key
import com.example.reading.presentation.view.base.BaseViewModel
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ManagerStoryViewModel @Inject constructor(
    private val repository: Repository
) : BaseViewModel() {
    private val _dataLiveData: MutableLiveData<List<Story>> = MutableLiveData()
    val dataLiveData: LiveData<List<Story>>
        get() = _dataLiveData
    lateinit var account: Account

    fun initializeArgument(bundle: Bundle) {
        val data = bundle.getString(Key.DATA)
        account = Gson().fromJson(data, Account::class.java)
    }

    fun getStories() {
        viewModelScope.launch {
            val result = repository.getStoryByAuthor(account.id)
            _dataLiveData.postValue(result)
        }
    }

    fun deleteStory(story: Story) = callSafeApiWithLiveData {
        repository.deleteStory(story)
    }
}