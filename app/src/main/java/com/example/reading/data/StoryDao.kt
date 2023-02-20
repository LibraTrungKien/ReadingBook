package com.example.reading.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.reading.data.entity.FavouriteEntity
import com.example.reading.data.entity.HistoryEntity
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

    @Query(
        """
        SELECT *
        FROM StoryEntity
        WHERE name LIKE :name
        """
    )
    suspend fun searchStoryByName(name: String): List<StoryEntity>

    @Query(
        """
        SELECT *
        FROM StoryEntity
        WHERE category = :category
    """
    )
    suspend fun getStoryByCategory(category: Int): List<StoryEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addHistory(story: HistoryEntity)

    @Insert
    suspend fun addFavourite(story: FavouriteEntity)

    @Query(
        """
        SELECT *
        FROM HistoryEntity
        ORDER BY timeWhenSave DESC
        LIMIT 4
    """
    )
    suspend fun getHistory(): List<HistoryEntity>

}