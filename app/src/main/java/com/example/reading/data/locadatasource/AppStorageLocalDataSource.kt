package com.example.reading.data.locadatasource

import com.example.reading.data.dto.AccountDTO
import com.example.reading.data.sharedpreferences.AppStorage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AppStorageLocalDataSource @Inject constructor(private val appStorage: AppStorage) {
    private val coroutineContext by lazy { Dispatchers.IO }
    suspend fun saveAccount(accountDTO: AccountDTO) = withContext(coroutineContext) {
        appStorage.saveAccount(accountDTO)
    }

    suspend fun getInfoAccount(): AccountDTO = withContext(coroutineContext) {
        appStorage.getAccount()
    }

    suspend fun removeAccount() = withContext(coroutineContext) {
        appStorage.removeAccount()
    }

    suspend fun setPermission(permission: Int) = withContext(coroutineContext) {
        appStorage.setPermission(permission)
    }

    suspend fun getPermission(): Int = withContext(coroutineContext) {
        appStorage.getPermission()
    }

    suspend fun getInfoReader() = withContext(coroutineContext) {
        appStorage.getInfoReader()
    }

    suspend fun removeInfoReader() = withContext(coroutineContext) {
        appStorage.removeInfoReader()
    }

    suspend fun saveInfoReader(readerName: String, imageProfile: String) =
        withContext(coroutineContext) {
            appStorage.saveInfoReader(readerName, imageProfile)
        }

    fun saveImageReader(imageProfile: String) = appStorage.saveImageReader(imageProfile)
}