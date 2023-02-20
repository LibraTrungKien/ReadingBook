package com.example.reading.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.reading.data.StoryDao
import com.example.reading.data.converter.ChapterConverter
import com.example.reading.data.entity.FavouriteEntity
import com.example.reading.data.entity.HistoryEntity
import com.example.reading.data.entity.StoryEntity

@Database(
    entities = [StoryEntity::class, HistoryEntity::class, FavouriteEntity::class],
    version = 1
)
@TypeConverters(ChapterConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun storyDao(): StoryDao
}