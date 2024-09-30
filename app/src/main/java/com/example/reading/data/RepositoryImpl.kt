package com.example.reading.data

import com.example.reading.data.locadatasource.AppStorageLocalDataSource
import com.example.reading.data.locadatasource.StoryLocalDataSource
import com.example.reading.data.mapper.toDTO
import com.example.reading.data.mapper.toEntity
import com.example.reading.data.mapper.toFavouriteEntity
import com.example.reading.data.mapper.toModel
import com.example.reading.data.mapper.toStoryEntity
import com.example.reading.domain.Repository
import com.example.reading.domain.model.Account
import com.example.reading.domain.model.Comment
import com.example.reading.domain.model.Login
import com.example.reading.domain.model.Products
import com.example.reading.domain.model.RateDataModel
import com.example.reading.domain.model.Story
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import okhttp3.MultipartBody
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val apiService: AppService,
    private val localDataSource: StoryLocalDataSource,
    private val appStorageLocalDataSource: AppStorageLocalDataSource
) : Repository {
    override suspend fun login(login: Login): Boolean {
        val response = apiService.login(login.phone, login.password).body()!!.first()
        appStorageLocalDataSource.saveAccount(response)
        return true
    }


    override suspend fun fetchAllStory() {
        val response = apiService.fetchAllStory().body()!!
        val storiesEntity = response.map { it.toEntity() }
        localDataSource.deleteAllStory()
        localDataSource.save(storiesEntity)
    }

    override suspend fun getAllStory(): List<Story> {
        return localDataSource.getAllStory().map { it.toModel() }
    }

    override suspend fun searchStoryByName(name: String): List<Story> {
        val text = "%$name%"
        val result = localDataSource.searchStoryByName(text)
        return result.map { it.toModel() }
    }

    override suspend fun getStoryByCategory(category: Int): List<Story> {
        val data = localDataSource.getStoryByCategory(category)
        return data.map { it.toModel() }
    }

    override suspend fun addHistory(story: Story) {
        localDataSource.save(story.toEntity())
    }

    override suspend fun addFavourite(story: Story): Boolean {
        localDataSource.save(story.toFavouriteEntity())
        return true
    }

    override suspend fun deleteHistory() {
    }

    override suspend fun deleteFavourite(story: Story) {
    }

    override suspend fun getHistory(): List<Story> {
        return localDataSource.getHistory().map { it.toModel() }
    }

    override fun getStoryFavourites(): Flow<List<Story>> {
        return localDataSource.getStoryFavourites().map { it.map { it.toModel() } }
    }

    override suspend fun deleteStoryFavourite(story: Story) {
        localDataSource.deleteFavourite(story.toFavouriteEntity())
    }

    override suspend fun getStoryByAuthor(author: Int): List<Story> {
        val data = apiService.getStoryByUserId(author).body() ?: listOf()
        return data
    }

    override suspend fun putStory(story: Story): Boolean {
        val response = apiService.putStory(story.id, story.toDTO()).body()!!
        localDataSource.save(response.toEntity())
        return true
    }

    override suspend fun postStory(story: Story): Boolean {
        val author = appStorageLocalDataSource.getInfoAccount().id
        story.author_id = author

        val response = apiService.postStory(story.toDTO()).body()!!
        localDataSource.save(response.toEntity())
        return true
    }

    override suspend fun getPermission(): Int {
        return appStorageLocalDataSource.getPermission()
    }

    override suspend fun deleteStory(story: Story) {
        apiService.deleteStory(story.id)
        localDataSource.deleteStory(story = story.toStoryEntity())
    }

    override suspend fun getInfoAccount(): Account {
        return appStorageLocalDataSource.getInfoAccount().toModel()
    }

    override suspend fun removeAccount() {
        appStorageLocalDataSource.removeAccount()
        localDataSource.deleteAllFavourite()
        localDataSource.deleteAllHistory()
    }

    override suspend fun registerAccount(account: Account): Boolean {
        apiService.registerAccount(account.toDTO()).body()!!
        return true
    }

    override suspend fun fetchAllAccount(): List<Account> {
        val response = apiService.fetchAllAccount().body()!!
        return response.map { it.toModel() }
    }

    override suspend fun deleteAccount(id: Int): Boolean {
        apiService.deleteAccount(id)
        return true
    }

    override suspend fun editAccount(account: Account): Account {
        val response = apiService.editAccount(account.toDTO(), account.id)
        appStorageLocalDataSource.removeAccount()
        appStorageLocalDataSource.saveAccount(response)
        return response.toModel()
    }

    override suspend fun uploadImage(file: MultipartBody.Part): String {
        val response = apiService.uploadFile(file).body()!!
        val filePath = response.filepath
        val lastVirgule = response.filepath.lastIndexOf("/")
        val path = filePath.substring(0, lastVirgule)
        return path + "/" + response.originalFilename
    }

    override suspend fun getProductById(): Products {
        val userId = getInfoAccount().id
        val response = apiService.fetchStoryById(userId).body()!!
        return response.toModel()
    }

    override suspend fun updateProduct(products: Products, userId: Int): Products {
        val dto = products.toDTO()
        val response = apiService.updateProducts(dto, userId).body()!!
        return response.toModel()
    }

    override suspend fun getStoryById(id: Int): Story? {
        val data = localDataSource.getStoryById(id)
        return data?.toModel()
    }

    override suspend fun insertComment(comment: Comment): List<Comment> {
        apiService.addComment(comment)
        return getCommentsByStoryId(comment.storyId)
    }

    override suspend fun getCommentsByStoryId(storyId: Int): List<Comment> {
        return apiService.getAllCommentByStoryId(storyId).body() ?: arrayListOf()
    }

    override suspend fun getAllStoryNotApprove(): List<Story> {
        return apiService.getAllStoryNotApprove().body() ?: listOf()
    }

    override suspend fun getUserById(userId: Int): Account {
        return apiService.getUserByID(userId).body()!!.first().toModel()
    }

    override suspend fun getRateByStoryId(storyId: Int): Int {
        val data = apiService.getRateByStoryId(storyId).body() ?: listOf()
        if (data.isEmpty()) return 0

        var countRate = 0
        data.forEach {
            countRate += it.rate
        }

        return countRate / (data.size)
    }

    override suspend fun rateStory(rateDataModel: RateDataModel) {
        apiService.rateStory(rateDataModel)
    }

    override suspend fun isRated(storyId: Int, authorId: Int): Boolean {
        val data = apiService.isRateStory(storyId, authorId).body() ?: listOf()
        return data.isNotEmpty()
    }

}