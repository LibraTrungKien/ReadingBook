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

    private val editor: SharedPreferences.Editor by lazy { sharedPreferences.edit() }

    fun saveAccount(accountDTO: AccountDTO) {
        with(editor) {
            putInt(Key.ACCOUNT_ID, accountDTO.id)
            putString(Key.USERNAME, accountDTO.username)
            putString(Key.EMAIL, accountDTO.email)
            putString(Key.PASSWORD, accountDTO.password)
            putString(Key.AVATAR, accountDTO.avatar)
            putBoolean(Key.PERMISSION, accountDTO.permission)
            apply()
        }
    }

    fun getPermission(): Int {
        return sharedPreferences.getInt(Key.PERMISSION, -1)
    }

    fun removeAccount() {
        with(editor) {
            remove(Key.ACCOUNT_ID)
            remove(Key.USERNAME)
            remove(Key.EMAIL)
            remove(Key.PASSWORD)
            remove(Key.AVATAR)
            remove(Key.GENDER)
            remove(Key.PHONE)
            remove(Key.PERMISSION)
            remove(Key.CAST)
            apply()
        }
    }

    fun getAccount(): AccountDTO {
        return AccountDTO(
            id = sharedPreferences.getInt(Key.ACCOUNT_ID, 0),
            username = sharedPreferences.getString(Key.USERNAME, "") ?: "",
            email = sharedPreferences.getString(Key.EMAIL, "") ?: "",
            password = sharedPreferences.getString(Key.PASSWORD, "") ?: "",
            avatar = sharedPreferences.getString(Key.AVATAR, "") ?: "",
            permission = sharedPreferences.getBoolean(Key.PERMISSION, false),
        )
    }

}