package com.example.reading.data.locadatasource

import com.example.reading.data.dto.AccountDTO
import com.example.reading.data.sharedpreferences.AppStorage
import javax.inject.Inject

class AppStorageLocalDataSource @Inject constructor(private val appStorage: AppStorage) {
    fun saveAccount(accountDTO: AccountDTO) {
        appStorage.saveAccount(accountDTO)
    }

    fun getInfoAccount(): AccountDTO{
        return appStorage.getAccount()
    }
}