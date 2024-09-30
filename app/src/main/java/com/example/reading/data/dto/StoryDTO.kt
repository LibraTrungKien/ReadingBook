package com.example.reading.data.dto

import com.google.gson.annotations.SerializedName

data class StoryDTO(
    val id: Int = 0,
    val name: String = "",
    val image: String = "",
    val category: Int = 0,
    val author_id: Int = 0,
    @SerializedName("date_created")
    val dateCreated: Long = 0L,
    val dateUpdated: Long= 0L,
    val status: Int = -1,
    val description: String = "",
    val chapters: ArrayList<ChapterDTO> = arrayListOf(),
    var modifiedAt: Long = 0
)