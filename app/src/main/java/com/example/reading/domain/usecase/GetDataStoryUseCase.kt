package com.example.reading.domain.usecase

import com.example.reading.domain.Repository
import com.example.reading.domain.model.Story
import com.example.reading.presentation.model.Category
import com.example.reading.presentation.model.StoryModelHolder
import com.example.reading.presentation.model.Type
import javax.inject.Inject

class GetDataStoryUseCase @Inject constructor(
    private val repository: Repository
) {
    private val data = arrayListOf<StoryModelHolder>()
    suspend operator fun invoke(): List<StoryModelHolder> {
        val stories = repository.getAllStory().filter { it.name.isNotBlank() }
        data.clear()
        loadNewStories(stories)
        loadFairyTalesStories(stories)
        loadFairyTalesWorldStories(stories)
        loadJokesStories(stories)
        loadFolkTaleStories(stories)
        loadGhostStories(stories)
        return data
    }

    private fun loadNewStories(stories: List<Story>) {
        if (stories.size < 6) return
        val newStories = stories.subList(0, 5)
        val model = StoryModelHolder().apply {
            type = Type.HEADER
            name = "Truyện mới nhất"
            subItem = StoryModelHolder(type = Type.CATEGORY, subItem = null, stories = newStories)
            this.stories = null
        }
        data.add(model)
    }

    private fun loadGhostStories(stories: List<Story>) {
        val ghostStories = stories.filter { it.category == Category.GHOST }
        val model = StoryModelHolder().apply {
            type = Type.HEADER
            name = "Truyện ma"
            subItem = StoryModelHolder(type = Type.CATEGORY, subItem = null, stories = ghostStories)
            this.stories = null
        }

        data.add(model)
    }

    private fun loadFairyTalesWorldStories(stories: List<Story>) {
        val fairyTalesStories = stories.filter { it.category == Category.FAIRY_TALES }
        val model = StoryModelHolder().apply {
            type = Type.HEADER
            name = "Truyện cổ tích thế giới"
            subItem =
                StoryModelHolder(type = Type.CATEGORY, subItem = null, stories = fairyTalesStories)
            this.stories = null
        }

        data.add(model)
    }

    private fun loadJokesStories(stories: List<Story>) {
        val jokesStories = stories.filter { it.category == Category.JOKES }
        val model = StoryModelHolder().apply {
            type = Type.HEADER
            name = "Truyện cười"
            subItem = StoryModelHolder(type = Type.CATEGORY, subItem = null, stories = jokesStories)
            this.stories = null
        }

        data.add(model)
    }

    private fun loadFairyTalesStories(stories: List<Story>) {
        val fairyTalesStories = stories.filter { it.category == Category.FAIRY_TALES_VI }
        val model = StoryModelHolder().apply {
            type = Type.HEADER
            name = "Truyện cổ tích Việt Nam"
            subItem =
                StoryModelHolder(type = Type.CATEGORY, subItem = null, stories = fairyTalesStories)
            this.stories = null
        }

        data.add(model)
    }

    private fun loadFolkTaleStories(stories: List<Story>) {
        val folkTaleStories = stories.filter { it.category == Category.FOLK_TALE }
        val model = StoryModelHolder().apply {
            type = Type.HEADER
            name = "Truyện dân gian"
            subItem = StoryModelHolder(
                type = Type.CATEGORY,
                subItem = null,
                stories = folkTaleStories
            )
            this.stories = null
        }
        data.add(model)
    }


}