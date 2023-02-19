package com.example.reading.data

data class StoryDTO(
    val id: String = "",
    val name: String = "",
    val image: String = "",
    val category: Int = 0,
    val author: String = "",
    val dateCreated: String = "",
    val dateUpdated: String = "",
    val status: String = "",
    val description: String = "",
    val chapters: List<ChapterDTO> = arrayListOf()
)