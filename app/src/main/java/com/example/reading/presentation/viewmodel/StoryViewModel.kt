package com.example.reading.presentation.viewmodel

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.reading.domain.Repository
import com.example.reading.domain.model.Account
import com.example.reading.domain.model.Comment
import com.example.reading.domain.model.RateDataModel
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

    private val _commentLiveData: MutableLiveData<List<Comment>> = MutableLiveData()
    val commentLiveData: LiveData<List<Comment>>
        get() = _commentLiveData

    private val _authorLiveData: MutableLiveData<Account> = MutableLiveData()
    val authorLiveData: LiveData<Account>
        get() = _authorLiveData

    private val _rateLiveData: MutableLiveData<Int> = MutableLiveData()
    val rateLiveData: LiveData<Int>
        get() = _rateLiveData

    private val _isRatedLiveData: MutableLiveData<Boolean> = MutableLiveData()
    val isRatedLiveData: LiveData<Boolean>
        get() = _isRatedLiveData

    fun initializeArgument(bundle: Bundle) {
        val result = bundle.getString(Key.DATA)
        story = Gson().fromJson(result, Story::class.java)
    }

    fun addFavourite() {
        viewModelScope.launch { repository.addFavourite(story) }
    }

    fun addComment(content: String) {
        viewModelScope.launch {
            val comment = Comment(
                storyId = story.id,
                userId = account.id,
                username = account.username,
                content = content,
                time = System.currentTimeMillis()
            )
            repository.insertComment(comment)
            getCommentById()
        }
    }

    fun getCommentById() {
        viewModelScope.launch {
            val data = repository.getCommentsByStoryId(story.id)
            _commentLiveData.postValue(data)
        }
    }

    fun getInfoAuthor() {
        viewModelScope.launch {
            val data = repository.getUserById(story.author_id)
            _authorLiveData.postValue(data)
        }
    }

    fun getAccount() {
        viewModelScope.launch {
            account = repository.getInfoAccount()
        }
    }

    fun getRateByStory(){
        viewModelScope.launch {
            val rate = repository.getRateByStoryId(story.id)
            _rateLiveData.postValue(rate)
        }
    }

    fun isRated(){
        viewModelScope.launch {
            val isRated = repository.isRated(story.id, account.id)
            _isRatedLiveData.postValue(isRated)
        }
    }

    fun rateStory(rate: Int){
        viewModelScope.launch {
            val rateDataModel = RateDataModel().apply {
                this.rate = rate
                this.storyId = story.id
                this.authorId = account.id
            }

            repository.rateStory(rateDataModel)
            getRateByStory()

            _isRatedLiveData.postValue(true)
        }
    }

}