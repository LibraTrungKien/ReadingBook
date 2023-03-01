package com.example.reading.data

import com.example.reading.data.dto.AccountDTO
import com.example.reading.data.dto.LoginDTO
import com.example.reading.data.dto.StoryDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface AppService {
    @POST("login")
    suspend fun login(@Body loginDTO: LoginDTO): Response<AccountDTO>

    @GET("story")
    suspend fun fetchAllStory(): Response<List<StoryDTO>>

    @PUT("story/{id}")
    suspend fun putStory(@Path("id") id: Int, @Body storyDTO: StoryDTO): Response<StoryDTO>

    @POST("story")
    suspend fun postStory(@Body storyDTO: StoryDTO): Response<StoryDTO>

    @DELETE("story/{id}")
    suspend fun deleteStory(@Path("id") id: Int)

    @POST("users")
    suspend fun registerAccount(@Body accountDTO: AccountDTO): Response<AccountDTO>
}