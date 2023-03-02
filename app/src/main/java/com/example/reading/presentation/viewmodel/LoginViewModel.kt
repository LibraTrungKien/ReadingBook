package com.example.reading.presentation.viewmodel

import com.example.reading.domain.Repository
import com.example.reading.domain.model.Login
import com.example.reading.presentation.view.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: Repository
) : BaseViewModel() {
    var login = Login("", "")

    fun copyPhoneNumber(value: String) {
        login.phone = value
    }

    fun copyPassword(value: String) {
        login.password = value
    }

    fun login() = callSafeApiWithLiveData {
        repository.login(login)
    }
}