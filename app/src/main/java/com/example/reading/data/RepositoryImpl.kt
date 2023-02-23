package com.example.reading.data

import com.example.reading.data.locadatasource.AppStorageLocalDataSource
import com.example.reading.data.locadatasource.StoryLocalDataSource
import com.example.reading.data.mapper.toDTO
import com.example.reading.data.mapper.toEntity
import com.example.reading.data.mapper.toFavouriteEntity
import com.example.reading.data.mapper.toModel
import com.example.reading.domain.Repository
import com.example.reading.domain.model.Login
import com.example.reading.domain.model.Story
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val apiService: AppService,
    private val localDataSource: StoryLocalDataSource,
    private val appStorageLocalDataSource: AppStorageLocalDataSource
) : Repository {
    override suspend fun login(login: Login): Boolean {
        val response = apiService.login(login.toDTO()).body()!!
        appStorageLocalDataSource.saveAccount(response)
        return true
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
        val text = "%$name%"
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

    override suspend fun addFavourite(story: Story): Boolean {
        localDataSource.save(story.toFavouriteEntity())
        return true
    }

    override suspend fun deleteHistory() {
    }

    override suspend fun deleteFavourite(story: Story) {
    }

    override suspend fun getHistory(): List<Story> {
        return localDataSource.getHistory().map { it.toModel() }
    }

    override fun getStoryFavourites(): Flow<List<Story>> {
        return localDataSource.getStoryFavourites().map { it.map { it.toModel() } }
    }

    override suspend fun deleteStoryFavourite(story: Story) {
        localDataSource.deleteFavourite(story.toFavouriteEntity())
    }

    override suspend fun getStoryByAuthor(author: String): List<Story> {
        val data = localDataSource.getStoryByAuthor(author)
        return data.map { it.toModel() }
    }

    override suspend fun putStory(story: Story): Boolean {
        val response = apiService.putStory(story.id, story.toDTO()).body()!!
        localDataSource.save(response.toEntity())
        return true
    }

    override suspend fun postStory(story: Story): Boolean {
        val response = apiService.postStory(story.toDTO()).body()!!
        localDataSource.save(response.toEntity())
        return true
    }

}