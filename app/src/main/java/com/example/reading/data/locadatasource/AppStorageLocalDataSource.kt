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


    suspend fun getPermission(): Int = withContext(coroutineContext) {
        appStorage.getPermission()
    }

}