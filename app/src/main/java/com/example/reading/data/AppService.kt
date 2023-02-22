package com.example.reading.data

import com.example.reading.data.dto.StoryDTO
import com.example.reading.data.dto.UserDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface AppService {
    @GET("Story")
    suspend fun getUsers(): Response<List<StoryDTO>>

    @GET("users")
    suspend fun fetchUsers(): Response<List<UserDTO>>

    @GET("story")
    suspend fun fetchAllStory(): Response<List<StoryDTO>>

    @PUT("story/{id}")
    suspend fun putStory(@Path("id") id: Int, @Body storyDTO: StoryDTO): Response<StoryDTO>

    @POST("story")
    suspend fun postStory(@Body storyDTO: StoryDTO): Response<StoryDTO>
}