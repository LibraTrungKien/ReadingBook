package com.example.reading.presentation.view.base

import android.content.Context
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData

fun <T> Fragment.apiCall(
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

fun handleException(
    throwable: Throwable?
) {
    throwable ?: return
    Log.d("FragmentExtension", "handleException()...${throwable.message} ")
}

fun Fragment.showConfirmDialog(
    context: Context,
    title: String,
    message: String,
    onYesClick: () -> Unit,
    onNoClicked: () -> Unit
) {
    AlertDialog.Builder(context).setTitle(title).setMessage(message).setPositiveButton(
        "Có"
    ) { _, _ -> onYesClick() }.setNegativeButton("Không") { _, _ -> onNoClicked() }.create().show()
}