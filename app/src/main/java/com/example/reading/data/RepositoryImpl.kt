package com.example.reading.data

import com.example.reading.data.mapper.toModel
import com.example.reading.domain.Repository
import com.example.reading.domain.model.Story
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val apiService: AppService
) : Repository {

    override suspend fun getUsers(): List<StoryDTO> {
        return apiService.getUsers().body()!!
    }

    override suspend fun fetchUsers(): List<UserDTO> {
        return apiService.fetchUsers().body()!!
    }

    override suspend fun fetchAllStory(): List<Story> {
        val response = apiService.fetchAllStory().body()!!
        return response.map { it.toModel() }
    }

}