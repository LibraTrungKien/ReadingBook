package com.example.reading.presentation.viewmodel

import android.os.Bundle
import androidx.lifecycle.viewModelScope
import com.example.reading.domain.Repository
import com.example.reading.domain.model.Account
import com.example.reading.domain.model.Products
import com.example.reading.domain.model.Story
import com.example.reading.presentation.Key
import com.example.reading.presentation.view.base.BaseViewModel
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StoryViewModel @Inject constructor(
    private val repository: Repository
) : BaseViewModel() {
    lateinit var story: Story
    var account: Account = Account()
    private var products: Products = Products()
    var readable: Boolean = false

    fun initializeArgument(bundle: Bundle) {
        val result = bundle.getString(Key.DATA)
        story = Gson().fromJson(result, Story::class.java)
        readable = bundle.getBoolean(Key.READ_ABLE)
    }

    fun addFavourite() {
        viewModelScope.launch { repository.addFavourite(story) }
    }

    fun getAccount() {
        viewModelScope.launch {
            account = repository.getInfoAccount()
        }
    }

    fun updateAccount() = callSafeApiWithLiveData {
        account.cost = account.cost - story.cost
        repository.editAccount(account)
    }

    fun getProduct() = callSafeApiWithLiveData {
        products = repository.getProductById()
    }

    fun updateProduct() = callSafeApiWithLiveData {
        products.stories.add(story.id)
        repository.updateProduct(products, account.id)
    }

    fun isExist() = products.stories.contains(story.id)
}