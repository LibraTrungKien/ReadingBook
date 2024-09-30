package com.example.reading.data.locadatasource

import com.example.reading.data.StoryDao
import com.example.reading.data.entity.FavouriteEntity
import com.example.reading.data.entity.HistoryEntity
import com.example.reading.data.entity.StoryEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class StoryLocalDataSource @Inject constructor(
    private val storyDao: StoryDao
) {
    private val coroutineContext by lazy { Dispatchers.IO }

    suspend fun save(stories: List<StoryEntity>) =
        withContext(coroutineContext) { storyDao.save(stories) }

    suspend fun getAllStory() = withContext(coroutineContext) {
        storyDao.getAllStory()
    }

    suspend fun searchStoryByName(name: String) = withContext(coroutineContext) {
        storyDao.searchStoryByName(name)
    }

    suspend fun getStoryByCategory(category: Int) = withContext(coroutineContext) {
        storyDao.getStoryByCategory(category)
    }

    suspend fun save(story: HistoryEntity) = withContext(coroutineContext) {
        storyDao.addHistory(story)
    }

    suspend fun save(story: FavouriteEntity) = withContext(coroutineContext) {
        storyDao.addFavourite(story)
    }

    suspend fun getHistory() = withContext(coroutineContext) {
        storyDao.getHistory()
    }

    suspend fun save(story: StoryEntity) = withContext(coroutineContext) {
        storyDao.save(story)
    }

    fun getStoryFavourites() = storyDao.getStoryFavourites()

    suspend fun deleteFavourite(favouriteEntity: FavouriteEntity) = withContext(coroutineContext) {
        storyDao.deleteFavourite(favouriteEntity)
    }

    suspend fun getStoryByAuthor(author: Int) = withContext(coroutineContext) {
        storyDao.getStoryByAuthor(author)
    }

    suspend fun deleteAllStory() = withContext(coroutineContext) {
        storyDao.deleteAllStory()
    }

    suspend fun deleteStory(story: StoryEntity) = withContext(coroutineContext) {
        storyDao.deleteStory(story)
    }

    suspend fun deleteAllHistory() = withContext(coroutineContext) {
        storyDao.deleteAllHistory()
    }

    suspend fun deleteAllFavourite() = withContext(coroutineContext) {
        storyDao.deleteAllFavourite()
    }

    suspend fun getStoryById(id: Int) = withContext(coroutineContext) { storyDao.getStoryById(id) }
}