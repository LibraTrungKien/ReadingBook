package com.example.reading.presentation.model

import com.example.reading.domain.model.Story

data class StoryModelHolder(
    var type: Type = Type.HEADER,
    var name: String = "",
    var subItem: StoryModelHolder? = null,
    var stories: List<Story>? = null
)