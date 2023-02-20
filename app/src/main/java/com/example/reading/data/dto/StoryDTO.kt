package com.example.reading.data.dto

import com.google.gson.annotations.SerializedName

data class StoryDTO(
    val id: Int = 0,
    val name: String = "",
    val image: String = "",
    val category: Int = 0,
    val author: String = "",
    @SerializedName("date_created")
    val dateCreated: String = "",
    val dateUpdated: String = "",
    val status: String = "",
    val description: String = "",
    val chapters: List<ChapterDTO> = arrayListOf()
)