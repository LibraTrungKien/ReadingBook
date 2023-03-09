package com.example.reading.data

import com.example.reading.data.dto.AccountDTO
import com.example.reading.data.dto.FileDTO
import com.example.reading.data.dto.StoryDTO
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface AppService {
    @GET("users")
    suspend fun login(
        @Query("phone") phone: String,
        @Query("password") password: String
    ): Response<List<AccountDTO>>

    @GET("story")
    suspend fun fetchAllStory(): Response<List<StoryDTO>>

    @PUT("story/{id}")
    suspend fun putStory(@Path("id") id: Int, @Body storyDTO: StoryDTO): Response<StoryDTO>

    @POST("story")
    suspend fun postStory(@Body storyDTO: StoryDTO): Response<StoryDTO>

    @DELETE("story/{id}")
    suspend fun deleteStory(@Path("id") id: Int)

    @DELETE("users/{id}")
    suspend fun deleteAccount(@Path("id") id: Int)

    @POST("users")
    suspend fun registerAccount(@Body accountDTO: AccountDTO): Response<AccountDTO>

    @GET("users")
    suspend fun fetchAllAccount(): Response<List<AccountDTO>>

    @PUT("users/{id}")
    suspend fun editAccount(@Body accountDTO: AccountDTO, @Path("id") id: Int): AccountDTO

    @Multipart
    @POST("upload-file")
    suspend fun uploadFile(@Part file: MultipartBody.Part): Response<FileDTO>
}