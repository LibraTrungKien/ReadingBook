package com.example.reading.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.reading.data.entity.StoryEntity

@Dao
interface StoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(story: List<StoryEntity>)

    @Query(
        """
        SELECT * 
        FROM StoryEntity 
        ORDER BY dateCreated DESC"""
    )
    suspend fun getAllStory(): List<StoryEntity>
}