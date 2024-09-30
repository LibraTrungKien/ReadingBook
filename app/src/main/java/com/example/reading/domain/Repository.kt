package com.example.reading.domain

import com.example.reading.domain.model.Account
import com.example.reading.domain.model.Comment
import com.example.reading.domain.model.Login
import com.example.reading.domain.model.Products
import com.example.reading.domain.model.RateDataModel
import com.example.reading.domain.model.Story
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody

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
    suspend fun getStoryByAuthor(author: Int): List<Story>
    suspend fun putStory(story: Story): Boolean
    suspend fun postStory(story: Story): Boolean
    suspend fun getInfoAccount(): Account
    suspend fun removeAccount()
    suspend fun getPermission(): Int
    suspend fun deleteStory(story: Story)
    suspend fun registerAccount(account: Account): Boolean
    suspend fun fetchAllAccount(): List<Account>
    suspend fun deleteAccount(id: Int): Boolean
    suspend fun editAccount(account: Account): Account
    suspend fun uploadImage(file: MultipartBody.Part): String
    suspend fun getProductById(): Products
    suspend fun updateProduct(products: Products, userId: Int): Products
    suspend fun getStoryById(id: Int): Story?
    suspend fun insertComment(comment: Comment): List<Comment>
    suspend fun getCommentsByStoryId(storyId: Int): List<Comment>
    suspend fun getAllStoryNotApprove(): List<Story>
    suspend fun getUserById(userId: Int): Account
    suspend fun getRateByStoryId(storyId: Int): Int
    suspend fun rateStory(rateDataModel: RateDataModel)
    suspend fun isRated(storyId: Int, authorId: Int): Boolean
}