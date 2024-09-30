package com.example.reading.data.mapper

import com.example.reading.data.dto.ChapterDTO
import com.example.reading.data.dto.StoryDTO
import com.example.reading.data.entity.FavouriteEntity
import com.example.reading.data.entity.HistoryEntity
import com.example.reading.data.entity.StoryEntity
import com.example.reading.domain.model.Chapter
import com.example.reading.domain.model.Story
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun Story.toDTO(): StoryDTO {
    val dto = this
    return StoryDTO(
        id = dto.id,
        name = dto.name,
        image = dto.image,
        category = dto.category,
        author_id = dto.author_id,
        dateCreated = dto.dateCreated,
        dateUpdated = dto.dateUpdated,
        status = dto.status,
        description = dto.description,
        chapters = ArrayList(dto.chapters.map { it.toDTO() })
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

fun Chapter.toDTO(): ChapterDTO {
    val dto = this
    return ChapterDTO(
        id = dto.id,
        index = dto.index,
        image = dto.image,
        title = dto.title,
        content = dto.content
    )
}

fun StoryDTO.toEntity(): StoryEntity {
    val dto = this
    return StoryEntity(
        id = dto.id,
        name = dto.name,
        image = dto.image,
        category = dto.category,
        author_id = dto.author_id,
        dateCreated = dto.dateCreated,
        dateUpdated = if (dto.dateUpdated == 0L) {
            dateCreated
        } else {
            dto.modifiedAt
        },
        status = dto.status,
        description = dto.description,
        chapters = ArrayList(dto.chapters.map { it.toModel() })
    )
}

fun StoryEntity.toModel(): Story {
    val entity = this
    return Story(
        id = entity.id,
        name = entity.name,
        image = entity.image,
        category = entity.category,
        author_id = entity.author_id,
        dateCreated = entity.dateCreated,
        dateUpdated = entity.dateUpdated,
        status = entity.status,
        description = entity.description,
        chapters = entity.chapters
    )
}

fun Story.toEntity(): HistoryEntity {
    val model = this
    return HistoryEntity(
        id = model.id,
        name = model.name,
        image = model.image,
        category = model.category,
        author_id = model.author_id,
        dateCreated = model.dateCreated,
        dateUpdated = model.dateUpdated,
        status = model.status,
        description = model.description,
        chapters = model.chapters
    )
}

fun Story.toFavouriteEntity(): FavouriteEntity {
    val model = this
    return FavouriteEntity(
        id = model.id,
        name = model.name,
        image = model.image,
        category = model.category,
        author_id = model.author_id,
        dateCreated = model.dateCreated,
        dateUpdated = model.dateUpdated,
        status = model.status,
        description = model.description,
        chapters = model.chapters
    )
}

fun HistoryEntity.toModel(): Story {
    val entity = this
    return Story(
        id = entity.id,
        name = entity.name,
        image = entity.image,
        category = entity.category,
        author_id = entity.author_id,
        dateCreated = entity.dateCreated,
        dateUpdated = entity.dateUpdated,
        status = entity.status,
        description = entity.description,
        chapters = entity.chapters
    )
}

fun FavouriteEntity.toModel(): Story {
    val entity = this
    return Story(
        id = entity.id,
        name = entity.name,
        image = entity.image,
        category = entity.category,
        author_id = entity.author_id,
        dateCreated = entity.dateCreated,
        dateUpdated = entity.dateUpdated,
        status = entity.status,
        description = entity.description,
        chapters = ArrayList(entity.chapters)
    )
}

fun Story.toStoryEntity(): StoryEntity {
    val model = this
    return StoryEntity(
        id = model.id,
        name = model.name,
        image = model.image,
        category = model.category,
        author_id = model.author_id,
        dateCreated = model.dateCreated,
        dateUpdated = model.dateUpdated,
        status = model.status,
        description = model.description,
        chapters = model.chapters
    )
}

fun Long.toDateString(): String {
    val date = Date(this)
    val localDateTime = SimpleDateFormat("dd/MM/yyyy", Locale("VINA"))
    return localDateTime.format(date)
}

