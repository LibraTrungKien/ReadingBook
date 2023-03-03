package com.example.reading.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.reading.domain.Repository
import com.example.reading.domain.model.Account
import com.example.reading.presentation.view.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(private val repository: Repository) : BaseViewModel() {
    lateinit var account: Account
        private set

    val dataLiveData: MutableLiveData<Account> = MutableLiveData()

    fun loadData() {
        viewModelScope.launch {
            account = repository.getInfoAccount()
            dataLiveData.postValue(account)
        }
    }
}