package com.example.reading.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.reading.domain.Repository
import com.example.reading.domain.model.Account
import com.example.reading.presentation.view.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ManageAccountViewModel @Inject constructor(
    private val repository: Repository
) : BaseViewModel() {

    private val _dataLiveData: MutableLiveData<List<Account>> = MutableLiveData()
    val dataLiveData: LiveData<List<Account>>
        get() = _dataLiveData

    fun loadData() {
        viewModelScope.launch {
            val result = repository.fetchAllAccount()
            _dataLiveData.postValue(result)
        }
    }
}