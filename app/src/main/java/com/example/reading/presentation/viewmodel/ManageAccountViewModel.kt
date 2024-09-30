package com.example.reading.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.reading.domain.Repository
import com.example.reading.domain.model.Account
import com.example.reading.presentation.view.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ManageAccountViewModel @Inject constructor(
    private val repository: Repository
) : BaseViewModel() {

    private var originData = listOf<Account>()
    var textSearch = ""

    private val _dataLiveData: MutableLiveData<List<Account>> = MutableLiveData()
    val dataLiveData: LiveData<List<Account>>
        get() = _dataLiveData

    fun loadData() = callSafeApiWithLiveData {
        originData = repository.fetchAllAccount().filter { !it.permission }
        originData
    }

    fun search() {
        val result = if (textSearch.isBlank()) {
            originData
        } else {
            originData.filter { it.email.contains(textSearch) || it.username.contains(textSearch) }
        }
        _dataLiveData.postValue(result)
    }

    fun deleteAccount(id: Int) = callSafeApiWithLiveData {
        repository.deleteAccount(id)
    }
}