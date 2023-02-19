package com.example.reading.data.mapper

import com.example.reading.data.ChapterDTO
import com.example.reading.data.StoryDTO
import com.example.reading.domain.model.Chapter
import com.example.reading.domain.model.Story

fun StoryDTO.toModel(): Story {
    val dto = this
    return Story(
        id = dto.id,
        name = dto.name,
        image = dto.image,
        category = dto.category,
        author = dto.author,
        dateCreated = dto.dateCreated,
        dateUpdated = dto.dateUpdated,
        status = dto.status,
        description = dto.description,
        chapters = dto.chapters.map { it.toModel() }
    )
}

fun ChapterDTO.toModel(): Chapter {
    val dto = this
    return Chapter(
        id = dto.id,
        index = dto.index,
        image = dto.image,
        title = dto.title,
        content = dto.content
    )
}