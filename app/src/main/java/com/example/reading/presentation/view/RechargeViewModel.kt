package com.example.reading.presentation.view

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.reading.domain.Repository
import com.example.reading.domain.model.Account
import com.example.reading.presentation.view.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RechargeViewModel @Inject constructor(
    private val repository: Repository
) : BaseViewModel() {
    var cost: Int = 10
    lateinit var account: Account
    val _dataLiveData: MutableLiveData<Boolean> = MutableLiveData()
    fun getAccount() {
        viewModelScope.launch {
            account = repository.getInfoAccount()
        }
    }

//    fun editAccount() = callSafeApiWithLiveData {
//        account.cost = account.cost + cost
//        repository.editAccount(account)
//        cost = 10
//    }

    fun getMoney(): Int {
        return when (cost) {
            22 -> 20000
            70 -> 50000
            150 -> 100000
            800 -> 500000
            else -> 10000
        }
    }

}