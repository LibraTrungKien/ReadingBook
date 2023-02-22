package com.example.reading.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.reading.domain.model.Chapter

@Entity
data class StoryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String = "",
    val image: String = "",
    val category: Int = 0,
    val author: String = "",
    val dateCreated: String = "",
    val dateUpdated: String = "",
    val status: String = "",
    val description: String = "",
    val chapters: ArrayList<Chapter> = arrayListOf()
)