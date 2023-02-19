package com.example.reading.data.converter

import androidx.room.TypeConverter
import com.example.reading.domain.model.Chapter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ChapterConverter {
    @TypeConverter
    fun chapters2String(chapters: List<Chapter>?): String? {
        if (chapters == null) return null
        return Gson().toJson(chapters)
    }

    @TypeConverter
    fun string2Chapters(data: String?): List<Chapter>? {
        if (data == null) return null
        val type = object : TypeToken<List<Chapter>>() {}.type
        return Gson().fromJson(data, type)
    }
}