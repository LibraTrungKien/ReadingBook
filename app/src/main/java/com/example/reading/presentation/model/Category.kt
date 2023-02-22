package com.example.reading.presentation.model

interface Category {
    companion object {
        const val GHOST = 0
        const val FAIRY_TALES = 1
        const val JOKES = 2
        const val FAIRY_TALES_VI = 3
        const val FOLK_TALE = 4

        fun getName(category: Int): String {
            return when (category) {
                GHOST -> "Truyện ma"
                FAIRY_TALES -> "Truyện cổ tích thế giới"
                JOKES -> "Truyện cười"
                FAIRY_TALES_VI -> "Truyện cổ tích Việt Nam"
                else -> "Truyện dân gian"
            }
        }
    }
}