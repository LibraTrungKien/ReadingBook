package com.example.reading.domain

import com.example.reading.data.dto.UserDTO
import com.example.reading.domain.model.Story

interface Repository {
    suspend fun fetchUsers(): List<UserDTO>
    suspend fun fetchAllStory()
    suspend fun getAllStory(): List<Story>
}