package com.example.reading.presentation.viewmodel

import com.example.reading.domain.Repository
import com.example.reading.presentation.view.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val repository: Repository
) : BaseViewModel() {
//    private var _dataLiveData: MutableLiveData<List<StoryDTO>> = MutableLiveData()
//    val dataLiveData: LiveData<List<StoryDTO>>
//        get() = _dataLiveData
//    var users = listOf<StoryDTO>()
//
//    fun getUser() = callSafeApiWithLiveData {
//        repository.fetchUsers()
//    }
}