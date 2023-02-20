package com.example.reading.data

import com.example.reading.data.dto.StoryDTO
import com.example.reading.data.dto.UserDTO
import retrofit2.Response
import retrofit2.http.GET

interface AppService {
    @GET("Story")
    suspend fun getUsers(): Response<List<StoryDTO>>

    @GET("users")
    suspend fun fetchUsers(): Response<List<UserDTO>>

    @GET("story")
    suspend fun fetchAllStory(): Response<List<StoryDTO>>
}