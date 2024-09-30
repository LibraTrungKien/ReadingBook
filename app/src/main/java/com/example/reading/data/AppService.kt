package com.example.reading.data

import com.example.reading.data.dto.AccountDTO
import com.example.reading.data.dto.FileDTO
import com.example.reading.data.dto.ProductsDTO
import com.example.reading.data.dto.StoryDTO
import com.example.reading.domain.model.Comment
import com.example.reading.domain.model.RateDataModel
import com.example.reading.domain.model.Story
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

    @GET("story/")
    suspend fun fetchAllStory(@Query("status") status: Int = 1): Response<List<StoryDTO>>

    @PUT("story/{id}")
    suspend fun putStory(@Path("id") id: Int, @Body storyDTO: StoryDTO): Response<StoryDTO>

    @GET("story")
    suspend fun getStoryByUserId(@Query("author_id") userId: Int): Response<List<Story>>

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

    @GET("products/{user_id}")
    suspend fun fetchStoryById(@Path("user_id") userId: Int): Response<ProductsDTO>

    @PUT("products/{user_id}")
    suspend fun updateProducts(
        @Body productsDTO: ProductsDTO,
        @Path("user_id") userId: Int
    ): Response<ProductsDTO>

    @POST("comment")
    suspend fun addComment(@Body comment: Comment)

    @GET("comment")
    suspend fun getAllCommentByStoryId(@Query("story_id") id: Int): Response<List<Comment>>

    // -1 not approve
    // 0 update
    // 1 done
    @GET("story/")
    suspend fun getAllStoryNotApprove(@Query("status") status: Int = -1): Response<List<Story>>

    @GET("users")
    suspend fun getUserByID(@Query("id") status: Int): Response<List<AccountDTO>>

    @GET("rate")
    suspend fun getRateByStoryId(@Query("storyId") storyId: Int): Response<List<RateDataModel>>

    @POST("rate")
    suspend fun rateStory(@Body rateDataModel: RateDataModel)

    @GET("rate")
    suspend fun isRateStory(@Query("storyId") storyId: Int, @Query("authorId") authorId: Int): Response<List<RateDataModel>>

}