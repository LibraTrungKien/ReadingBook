package com.example.reading.domain

import com.example.reading.data.StoryDTO
import com.example.reading.data.UserDTO
import com.example.reading.domain.model.Story

interface Repository {
    suspend fun getUsers(): List<StoryDTO>
    suspend fun fetchUsers(): List<UserDTO>
    suspend fun fetchAllStory(): List<Story>
}