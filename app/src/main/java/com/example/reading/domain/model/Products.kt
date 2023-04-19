package com.example.reading.domain.model

data class Products(
    val id: Int = 0,
    val userId: Int = 0,
    val stories: ArrayList<Int> = arrayListOf()
)