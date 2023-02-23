package com.example.reading.data.sharedpreferences

import android.content.Context
import android.content.SharedPreferences
import com.example.reading.data.dto.AccountDTO
import com.example.reading.presentation.Key
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class AppStorage @Inject constructor(
    @ApplicationContext context: Context
) {

    companion object {
        const val name = "ACCOUNT"
    }

    private val sharedPreferences: SharedPreferences by lazy {
        context.getSharedPreferences(name, Context.MODE_PRIVATE)
    }

    fun saveAccount(accountDTO: AccountDTO) {
        with(sharedPreferences.edit()) {
            putInt(Key.ACCOUNT_ID, accountDTO.id)
            putString(Key.USERNAME, accountDTO.username)
            putString(Key.EMAIL, accountDTO.email)
            putString(Key.PASSWORD, accountDTO.password)
            putString(Key.AVATAR, accountDTO.avatar)
            putString(Key.GENDER, accountDTO.gender)
            putString(Key.PHONE, accountDTO.phone)
            apply()
        }
    }

    fun getAccount(): AccountDTO {
        return AccountDTO(
            id = sharedPreferences.getInt(Key.ACCOUNT_ID, 0),
            username = sharedPreferences.getString(Key.USERNAME, "") ?: "",
            email = sharedPreferences.getString(Key.USERNAME, "") ?: "",
            password = sharedPreferences.getString(Key.USERNAME, "") ?: "",
            avatar = sharedPreferences.getString(Key.USERNAME, "") ?: "",
            gender = sharedPreferences.getString(Key.USERNAME, "") ?: "",
            phone = sharedPreferences.getString(Key.USERNAME, "") ?: "",
        )
    }

}