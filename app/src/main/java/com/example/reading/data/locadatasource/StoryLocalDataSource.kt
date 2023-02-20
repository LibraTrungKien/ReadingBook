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

    suspend fun getHistory() = withContext(coroutineContext){
        storyDao.getHistory()
    }
}