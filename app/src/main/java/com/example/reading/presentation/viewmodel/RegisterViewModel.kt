package com.example.reading.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.reading.domain.Repository
import com.example.reading.presentation.view.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(private val repository: Repository) : BaseViewModel() {
    var name = ""
    private val _dataLiveData: MutableLiveData<Boolean> = MutableLiveData()
    val dataLiveData: LiveData<Boolean>
        get() = _dataLiveData

    fun copyName(value: String) {
        name = value
    }

    fun saveInfoReader() {
        viewModelScope.launch {
            repository.saveInfoReader(name, "")
            _dataLiveData.postValue(false)
        }
    }
}