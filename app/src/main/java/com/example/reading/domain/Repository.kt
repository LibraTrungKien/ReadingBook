package com.example.reading.domain

import com.example.reading.domain.model.Account
import com.example.reading.domain.model.Login
import com.example.reading.domain.model.Story
import kotlinx.coroutines.flow.Flow

interface Repository {
    suspend fun login(login: Login): Boolean
    suspend fun fetchAllStory()
    suspend fun getAllStory(): List<Story>
    suspend fun searchStoryByName(name: String): List<Story>
    suspend fun getStoryByCategory(category: Int): List<Story>
    suspend fun addHistory(story: Story)
    suspend fun addFavourite(story: Story): Boolean
    suspend fun deleteHistory()
    suspend fun deleteFavourite(story: Story)
    suspend fun getHistory(): List<Story>
    fun getStoryFavourites(): Flow<List<Story>>
    suspend fun deleteStoryFavourite(story: Story)
    suspend fun getStoryByAuthor(author: String): List<Story>
    suspend fun putStory(story: Story): Boolean
    suspend fun postStory(story: Story): Boolean
    suspend fun getInfoAccount(): Account
    suspend fun removeAccount()
    suspend fun getPermission(): Int
    suspend fun deleteStory(story: Story)
    suspend fun registerAccount(account: Account): Boolean
    suspend fun fetchAllAccount(): List<Account>
}