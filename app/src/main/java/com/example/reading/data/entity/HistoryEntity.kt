package com.example.reading.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.reading.domain.model.Chapter

@Entity
data class HistoryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String = "",
    val image: String = "",
    val category: Int = 0,
    val author_id: Int = 0,
    val dateCreated: Long = 0L,
    val dateUpdated: Long = 0L,
    val cost: Int = 0,
    val status: Int = -1,
    val description: String = "",
    val chapters: ArrayList<Chapter> = arrayListOf(),
    val timeWhenSave: Long = System.currentTimeMillis()
)