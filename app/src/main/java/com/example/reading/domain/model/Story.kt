package com.example.reading.domain.model

import com.example.reading.presentation.model.Category

data class Story(
    var id: Int = 0,
    var name: String = "",
    var image: String = "",
    var category: Int = -1,
    var cost: Int = 0,
    var author: String = "",
    var dateCreated: String = "",
    var dateUpdated: String = "",
    var status: String = "Đang update",
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