package com.example.reading.presentation.viewmodel

import FileUtils
import android.content.Context
import android.net.Uri
import android.os.Bundle
import com.example.reading.domain.Repository
import com.example.reading.domain.model.Account
import com.example.reading.presentation.Key
import com.example.reading.presentation.view.base.BaseViewModel
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject

@HiltViewModel
class EditProfileViewModel @Inject constructor(private val repository: Repository) :
    BaseViewModel() {
    lateinit var account: Account
        private set
    var imageUri: Uri? = null

    fun initializeArgument(bundle: Bundle) {
        val result = bundle.getString(Key.DATA)
        account = Gson().fromJson(result, Account::class.java)
    }

    fun copyUsername(value: String) {
        account.username = value
    }

    fun copyGmail(value: String) {
        account.email = value
    }

    fun copyPassword(value: String) {
        account.password = value
    }


    private suspend fun uploadImage(context: Context, imageUri: Uri): String {
        val path = FileUtils.getRealPath(context, imageUri)
        val file = File(path!!)
        val requestBody = file.asRequestBody("multipart/form-data".toMediaType())
        val multipleBody = MultipartBody.Part.createFormData("file", file.name, requestBody)
        return repository.uploadImage(multipleBody)
    }

    fun saveAccount(context: Context) = callSafeApiWithLiveData {
        imageUri?.let {
            account.avatar = uploadImage(context, it)
        }
        repository.editAccount(account)
    }
}