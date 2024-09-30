package com.example.reading.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.example.reading.domain.Repository
import com.example.reading.domain.model.Account
import com.example.reading.domain.usecase.GetDataStoryUseCase
import com.example.reading.presentation.model.StoryModelHolder
import com.example.reading.presentation.view.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getDataStoryUseCase: GetDataStoryUseCase,
    private val repository: Repository
) : BaseViewModel() {
    var isFirst = 0
    val images = arrayListOf<SlideModel>()
    lateinit var account: Account

    var imageProfile: String = ""

    private var _dataLiveData: MutableLiveData<List<StoryModelHolder>> = MutableLiveData()
    val dataLiveData: LiveData<List<StoryModelHolder>>
        get() = _dataLiveData

    private var _dataUserLiveData: MutableLiveData<Boolean> = MutableLiveData()
    val dataUserLiveData: LiveData<Boolean>
        get() = _dataUserLiveData

    fun loadAd() {
        images.clear()
        images.add(
            SlideModel(
                "https://upload.wikimedia.org/wikipedia/vi/6/63/Nhan_vat_Tham_tu_lung_danh_Conan.jpg",
                ScaleTypes.CENTER_CROP
            )
        )
        images.add(
            SlideModel(
                "https://sohanews.sohacdn.com/k:2015/2-neu-tim-duoc-7-vien-ngoc-rong-trong-dragon-ball-ban-se-uoc-dieu-gi-1435922279961/neu-tim-duoc-7-vien-ngoc-rong-trong-dragon-ball-ban-se-uoc-dieu-gi.png",
                ScaleTypes.CENTER_CROP
            )
        )
        images.add(
            SlideModel(
                "https://static.mservice.io/blogscontents/momo-upload-api-221010113704-638009986247161745.jpg",
                ScaleTypes.CENTER_CROP
            )
        )
    }

    fun loadDataUser() {
        viewModelScope.launch {
            account = repository.getInfoAccount()
            _dataUserLiveData.value = true
        }
    }

    fun loadData() {
        viewModelScope.launch {
            val data = getDataStoryUseCase.invoke()
            _dataLiveData.postValue(data)
        }
    }

    fun logout() {
        viewModelScope.launch {
            repository.removeAccount()
        }
    }

    fun isFirst() = isFirst == 0
    fun isAdmin() = account.permission
}