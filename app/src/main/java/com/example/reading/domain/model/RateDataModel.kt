package com.example.reading.domain.model

data class RateDataModel(
    var id: Int = 0,
    var rate: Int = 5,
    var authorId: Int = 0,
    var storyId: Int = 0
)