package com.example.reading.presentation.view.base

sealed class ApiResult<out T> {
    data class Success<out T>(val data: T?) : ApiResult<T>()
    data class Error(val e: Exception) : ApiResult<Nothing>()
}
