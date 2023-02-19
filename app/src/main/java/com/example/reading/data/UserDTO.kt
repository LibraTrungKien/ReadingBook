package com.example.reading.data

import com.google.gson.annotations.SerializedName

data class UserDTO(
    private val id: String,
    @SerializedName("name")
    private val firstName: String
)