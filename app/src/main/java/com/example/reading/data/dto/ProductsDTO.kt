package com.example.reading.data.dto

import com.google.gson.annotations.SerializedName

data class ProductsDTO(
    val id: Int = 0,
    @SerializedName("user_id")
    val userId: Int = 0,
    val stories: ArrayList<Int> = arrayListOf()
)