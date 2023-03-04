package com.example.reading.presentation.viewmodel

import com.example.reading.domain.Repository
import com.example.reading.domain.model.Account
import com.example.reading.presentation.view.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(private val repository: Repository) : BaseViewModel() {
    var account = Account(permission = "reader")
    var againPassword = ""
    var code = ""
    var isCheckVerifyPhoneNumber = false

    fun copyName(value: String) {
        account.username = value
    }

    fun copyPhoneNumber(value: String) {
        account.phone = value
    }

    fun copyPassword(value: String) {
        account.password = value
    }

    fun copyAgainPassword(value: String) {
        againPassword = value
    }

    fun copyGender(value: String) {
        account.gender = value
    }

    fun registerAccount() = callSafeApiWithLiveData {
        repository.registerAccount(account)
    }

    fun validateData() =
        account.username.isNotBlank() && account.password.isNotBlank() && account.phone.isNotBlank()

    fun checkDuplicate() = account.password == againPassword
}