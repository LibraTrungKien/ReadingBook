package com.example.reading.data

import com.example.reading.data.dto.UserDTO
import com.example.reading.data.locadatasource.StoryLocalDataSource
import com.example.reading.data.mapper.toEntity
import com.example.reading.data.mapper.toModel
import com.example.reading.domain.Repository
import com.example.reading.domain.model.Story
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val apiService: AppService,
    private val localDataSource: StoryLocalDataSource
) : Repository {

    override suspend fun fetchUsers(): List<UserDTO> {
        return apiService.fetchUsers().body()!!
    }

    override suspend fun fetchAllStory() {
        val response = apiService.fetchAllStory().body()!!
        val storiesEntity = response.map { it.toEntity() }
        localDataSource.save(storiesEntity)
    }

    override suspend fun getAllStory(): List<Story> {
        return localDataSource.getAllStory().map { it.toModel() }
    }

    override suspend fun searchStoryByName(name: String): List<Story> {
        val text = "*$name*"
        val result = localDataSource.searchStoryByName(text)
        return result.map { it.toModel() }
    }

    override suspend fun getStoryByCategory(category: Int): List<Story> {
        val data = localDataSource.getStoryByCategory(category)
        return data.map { it.toModel() }
    }

    override suspend fun addHistory(story: Story) {
        localDataSource.save(story.toEntity())
    }

    override suspend fun addFavourite(story: Story) {

    }

    override suspend fun deleteHistory() {
    }

    override suspend fun deleteFavourite(story: Story) {
    }

    override suspend fun getHistory(): List<Story> {
        return localDataSource.getHistory().map { it.toModel() }
    }

}