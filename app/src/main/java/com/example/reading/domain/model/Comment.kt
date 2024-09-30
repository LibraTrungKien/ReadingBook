package com.example.reading.domain.model

data class Comment(
    var id: Int = 0,
    var storyId: Int = 0,
    var username: String = "",
    var userId: Int = 0,
    var content: String = "",
    var time: Long = 0L
)