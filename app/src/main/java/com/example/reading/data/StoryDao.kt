package com.example.reading.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.reading.data.entity.FavouriteEntity
import com.example.reading.data.entity.HistoryEntity
import com.example.reading.data.entity.StoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface StoryDao {

    @Query(
        """
        SELECT * 
        FROM StoryEntity 
        ORDER BY dateUpdated"""
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

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavourite(story: FavouriteEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(story: List<StoryEntity>)

    @Query(
        """
        SELECT *
        FROM HistoryEntity
        ORDER BY timeWhenSave DESC
        LIMIT 4
    """
    )
    suspend fun getHistory(): List<HistoryEntity>

    @Query(
        """
        SELECT *
        FROM FavouriteEntity
    """
    )
    fun getStoryFavourites(): Flow<List<FavouriteEntity>>

    @Delete
    suspend fun deleteFavourite(favouriteEntity: FavouriteEntity)

    @Query(
        """
        SELECT *
        FROM StoryEntity
        WHERE author = :author
    """
    )
    suspend fun getStoryByAuthor(author: String): List<StoryEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(storyEntity: StoryEntity)

    @Query(
        """
        DELETE 
        FROM StoryEntity
    """
    )
    suspend fun deleteAllStory()
}