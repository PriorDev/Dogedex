package com.prior_dev.dogedex.main

import androidx.camera.core.ImageProxy
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prior_dev.dogedex.api.ApiResponseStatus
import com.prior_dev.dogedex.auth.UserRepositoryTask
import com.prior_dev.dogedex.doglist.DogRepositoryTask
import com.prior_dev.dogedex.machinglearning.ClassifierRepositoryTask
import com.prior_dev.dogedex.machinglearning.DogRecognition
import com.prior_dev.dogedex.models.Dog
import com.prior_dev.dogedex.models.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val dogRepo: DogRepositoryTask,
    private val classifierRepository: ClassifierRepositoryTask,
    private val userRepo: UserRepositoryTask
): ViewModel() {

    private val _dog = MutableLiveData<Dog>()
    val dog: LiveData<Dog>
        get() = _dog

    private val _status = MutableLiveData<ApiResponseStatus<Dog>>()
    val status: LiveData<ApiResponseStatus<Dog>>
        get() = _status

    private val _dogRecognition = MutableLiveData<DogRecognition>()
    val dogRecognition: LiveData<DogRecognition>
        get() = _dogRecognition

    var user = MutableLiveData<User?>()
        private set

    init {
        user.value = userRepo.getLoggedInUser()
    }

    fun getRecognizedDog(mlDogId: String){
        viewModelScope.launch {
            handleResponse(dogRepo.getDogByMlId(mlDogId))
        }
    }

    private fun handleResponse(apiResponseStatus: ApiResponseStatus<Dog>){
        if(apiResponseStatus is ApiResponseStatus.Success){
            _dog.value = apiResponseStatus.data!!
        }

        _status.value = apiResponseStatus
    }

    fun recognizeImage(imageProxy: ImageProxy){
        viewModelScope.launch {
            _dogRecognition.value = classifierRepository.recognizeImage(imageProxy)
            imageProxy.close()
        }
    }
}