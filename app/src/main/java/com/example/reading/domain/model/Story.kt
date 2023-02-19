package com.example.reading.domain.model

data class Story(
    var id: String = "",
    var name: String = "",
    var image: String = "",
    var category: Int = 0,
    var author: String = "",
    var dateCreated: String = "",
    var dateUpdated: String = "",
    var status: String = "",
    var description: String = "",
    var chapters: List<Chapter> = arrayListOf()
) {
    fun getCategory(): String {
        return when (category) {
            0 -> "Truyện ma"
            1 -> "Anime"
            2 -> "Truyện cười"
            3 -> "Truyện cổ tích"
            else -> "Truyện ngắn"
        }
    }
}