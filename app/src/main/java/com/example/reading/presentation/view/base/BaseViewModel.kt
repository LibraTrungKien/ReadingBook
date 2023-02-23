package com.example.reading.presentation.view.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okio.IOException

abstract class BaseViewModel : ViewModel() {
    private val coroutineContext by lazy { viewModelScope.coroutineContext + Dispatchers.IO }

    fun <T> callSafeApiWithLiveData(call: suspend () -> T): LiveData<ApiResult<T>> {
        return liveData(coroutineContext) {
            try {
                val result = call()
                emit(ApiResult.Success(result))
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    emit(ApiResult.Error(e))
                }
            }
        }
    }
}