package com.prior_dev.dogedex.main

import androidx.camera.core.ImageProxy
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prior_dev.dogedex.api.ApiResponseStatus
import com.prior_dev.dogedex.doglist.DogRepository
import com.prior_dev.dogedex.doglist.DogRepositoryTask
import com.prior_dev.dogedex.machinglearning.Classifier
import com.prior_dev.dogedex.machinglearning.ClassifierRepository
import com.prior_dev.dogedex.machinglearning.ClassifierRepositoryTask
import com.prior_dev.dogedex.machinglearning.DogRecognition
import com.prior_dev.dogedex.models.Dog
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.nio.MappedByteBuffer
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository : DogRepositoryTask,
    private val classifierRepository: ClassifierRepositoryTask
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

    fun getRecognizedDog(mlDogId: String){
        viewModelScope.launch {
            handleResponse(repository.getDogByMlId(mlDogId))
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