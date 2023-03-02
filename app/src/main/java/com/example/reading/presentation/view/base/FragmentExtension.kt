package com.example.reading.presentation.view.base

import android.content.Context
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import com.example.reading.R
import com.example.reading.presentation.view.diglog.MessageDialog
import java.net.SocketTimeoutException

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
                    handleException(it.e) {
                    }
                }
            }
        }
    }
}

fun Fragment.handleException(
    throwable: Throwable?,
    onButtonClicked: () -> Unit
) {
    var message = ""
    throwable ?: return
    message = when (throwable) {
        is NullPointerException, is NoSuchElementException -> {
            "Không thành công!\nTài khoản hoặc mật khẩu không chính xác..."
        }

        is SocketTimeoutException -> {
            "Không thành công!\nVui lòng kiểm Server(có vẻ bạn chưa run server)..."
        }

        else -> {
            "Không thành công!\nVui lòng kiểm tra kết nối mạng..."
        }
    }

    MessageDialog.show(
        this.parentFragmentManager,
        "Thông báo",
        message,
        R.drawable.ic_sad,
        onButtonClicked
    )
}

fun Fragment.showConfirmDialog(
    context: Context,
    title: String,
    message: String,
    onYesClick: () -> Unit,
    onNoClicked: () -> Unit
) {
    AlertDialog.Builder(context)
        .setTitle(title)
        .setMessage(message)
        .setPositiveButton(
            "Có"
        ) { _, _ -> onYesClick() }
        .setNegativeButton("Không") { _, _ -> onNoClicked() }
        .create()
        .show()
}