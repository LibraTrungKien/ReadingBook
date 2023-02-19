package com.example.reading.data.di

import android.content.Context
import androidx.room.Room
import com.example.reading.data.StoryDao
import com.example.reading.data.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalModule {
    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, "story.db").build()
    }

    @Provides
    fun provideStoryDao(appDatabase: AppDatabase): StoryDao {
        return appDatabase.storyDao()
    }
}