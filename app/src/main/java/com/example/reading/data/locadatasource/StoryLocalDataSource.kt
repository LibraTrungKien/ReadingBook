package com.example.reading.data.locadatasource

import com.example.reading.data.StoryDao
import com.example.reading.data.entity.StoryEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class StoryLocalDataSource @Inject constructor(
    private val storyDao: StoryDao
) {
    private val coroutineContext by lazy { Dispatchers.IO }

    suspend fun save(stories: List<StoryEntity>) =
        withContext(coroutineContext) { storyDao.save(stories) }

    suspend fun getAllStory() = withContext(coroutineContext){
        storyDao.getAllStory()
    }
}