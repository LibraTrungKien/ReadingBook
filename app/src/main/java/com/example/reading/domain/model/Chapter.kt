package com.example.reading.domain.model

data class Chapter(
    var id: String = "",
    var index: Int = 0,
    var image: String = "",
    var title: String = "",
    var content: String = "",
    var isCensorship: Boolean = true
)