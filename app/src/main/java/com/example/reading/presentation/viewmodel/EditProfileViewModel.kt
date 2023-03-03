package com.example.reading.presentation.viewmodel

import android.os.Bundle
import com.example.reading.domain.Repository
import com.example.reading.domain.model.Account
import com.example.reading.presentation.Key
import com.example.reading.presentation.view.base.BaseViewModel
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EditProfileViewModel @Inject constructor(private val repository: Repository) :
    BaseViewModel() {
    lateinit var account: Account
        private set

    fun initializeArgument(bundle: Bundle) {
        val result = bundle.getString(Key.DATA)
        account = Gson().fromJson(result, Account::class.java)
    }
}