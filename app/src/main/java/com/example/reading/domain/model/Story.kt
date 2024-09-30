package com.example.reading.domain.model

import com.example.reading.presentation.model.Category

data class Story(
    var id: Int = 0,
    var name: String = "",
    var image: String = "",
    var category: Int = -1,
    var author_id: Int = 0,
    var dateCreated: Long = 0L,
    var dateUpdated: Long = 0L,
    var status: Int = -1,
    var description: String = "",
    var chapters: ArrayList<Chapter> = arrayListOf()
) {
    fun getCategory(): String {
        return when (category) {
            Category.GHOST -> "Truyện ma"
            Category.FAIRY_TALES -> "Truyện cổ tích thế giới"
            Category.JOKES -> "Truyện cười"
            Category.FAIRY_TALES_VI -> "Truyện cổ tích Việt Nam"
            Category.FOLK_TALE -> "Truyện dân gian"
            else -> ""
        }
    }
}