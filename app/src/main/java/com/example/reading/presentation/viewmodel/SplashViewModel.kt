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
class SplashViewModel @Inject constructor(private val repository: Repository) : BaseViewModel() {
    lateinit var account: Account
    private val _dataLiveData: MutableLiveData<Boolean> = MutableLiveData()
    val dataLiveData: LiveData<Boolean>
        get() = _dataLiveData

    fun getInfoAccount() {
        viewModelScope.launch {
            account = repository.getInfoAccount()
            _dataLiveData.value = hasAccount(account)
        }
    }

    private fun hasAccount(account: Account) = account.username.isNotBlank()
}
