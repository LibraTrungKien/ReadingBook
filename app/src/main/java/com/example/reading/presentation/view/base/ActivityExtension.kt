package com.example.reading.presentation.view.base

import android.app.Activity
import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData

fun <T> Activity.apiCall(
    liveData: LiveData<ApiResult<T>>,
    lifecycleOwner: LifecycleOwner,
    onSuccess: (data: T?) -> Unit,
    onError: (e: Exception) -> Boolean = { false }
) {
    liveData.observe(lifecycleOwner) {
        when (it) {
            is ApiResult.Success -> onSuccess(it.data)
            is ApiResult.Error -> {
                val isConsumer = onError(it.e)
                if (isConsumer) {
                    handleException(it.e)
                }
            }
        }
    }
}

fun Activity.handleException(throwable: Throwable?) {
    throwable ?: return
    Log.d("FragmentExtension", "handleException()...${throwable.message} ")
}