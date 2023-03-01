package com.example.reading.presentation.viewmodel

import com.example.reading.domain.Repository
import com.example.reading.domain.model.Account
import com.example.reading.presentation.view.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddAccountViewModel @Inject constructor(
    private val repository: Repository
) : BaseViewModel() {
    var account = Account(permission = "author")
    var againPassword = ""
    fun copyDisplayName(value: String) {
        account.username = value
    }

    fun copyPhoneNumber(value: String) {
        account.phone = value
    }

    fun copyUserName(value: String) {
        account.email = value
    }

    fun copyPassword(value: String) {
        account.password = value
    }

    fun copyAgainPassword(value: String) {
        againPassword = value
    }

    fun isMale(isMale: Boolean) {
        account.gender = if (isMale) "Nam" else "Nữ"
    }

    fun registerAccount() = callSafeApiWithLiveData {
        repository.registerAccount(account)
    }

    fun checkPassword() = account.password == againPassword

    fun validateData() =
        account.email.isNotBlank() && account.phone.isNotBlank() && account.password.isNotBlank() && account.username.isNotBlank()
}