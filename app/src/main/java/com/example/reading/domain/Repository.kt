package com.example.reading.domain

import com.example.reading.data.dto.UserDTO
import com.example.reading.domain.model.Story

interface Repository {
    suspend fun fetchUsers(): List<UserDTO>
    suspend fun fetchAllStory()
    suspend fun getAllStory(): List<Story>
    suspend fun searchStoryByName(name: String): List<Story>
    suspend fun getStoryByCategory(category: Int): List<Story>
    suspend fun addHistory(story: Story)
    suspend fun addFavourite(story: Story)
    suspend fun deleteHistory()
    suspend fun deleteFavourite(story: Story)
    suspend fun getHistory(): List<Story>
}