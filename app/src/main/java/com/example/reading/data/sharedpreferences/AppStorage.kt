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
            putString(Key.GENDER, accountDTO.gender)
            putString(Key.PHONE, accountDTO.phone)
            apply()
        }
    }

    fun saveInfoReader(readerName: String, imageProfile: String) {
        with(editor) {
            putString(Key.READER_NAME, readerName)
            putString(Key.IMAGE_PROFILE, imageProfile)
            apply()
        }
    }

    fun saveImageReader(imageProfile: String) {
        editor.putString(Key.IMAGE_PROFILE, imageProfile)
        editor.apply()
    }

    fun getInfoReader(): Pair<String, String> {
        val readerName = sharedPreferences.getString(Key.READER_NAME, "") ?: ""
        val imageProfile = sharedPreferences.getString(Key.IMAGE_PROFILE, "") ?: ""
        return Pair(readerName, imageProfile)
    }

    fun removeInfoReader() {
        with(editor) {
            remove(Key.READER_NAME)
            remove(Key.IMAGE_PROFILE)
            apply()
        }
    }

    fun setPermission(permission: Int) {
        //  0: author   1: user 2: admin
        with(editor) {
            putInt(Key.PERMISSION, permission)
            apply()
        }
    }

    suspend fun getPermission(): Int {
        return sharedPreferences.getInt(Key.PERMISSION, -1)
    }

    suspend fun removeAccount() {
        with(editor) {
            remove(Key.ACCOUNT_ID)
            remove(Key.USERNAME)
            remove(Key.EMAIL)
            remove(Key.PASSWORD)
            remove(Key.AVATAR)
            remove(Key.GENDER)
            remove(Key.PHONE)
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
            gender = sharedPreferences.getString(Key.GENDER, "") ?: "",
            phone = sharedPreferences.getString(Key.PHONE, "") ?: "",
        )
    }


}