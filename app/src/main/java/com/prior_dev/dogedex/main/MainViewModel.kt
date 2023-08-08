package com.prior_dev.dogedex.main

import androidx.camera.core.ImageProxy
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prior_dev.dogedex.api.ApiResponseStatus
import com.prior_dev.dogedex.doglist.DogRepository
import com.prior_dev.dogedex.machinglearning.Classifier
import com.prior_dev.dogedex.machinglearning.ClassifierRepository
import com.prior_dev.dogedex.machinglearning.DogRecognition
import com.prior_dev.dogedex.models.Dog
import kotlinx.coroutines.launch
import java.nio.MappedByteBuffer

class MainViewModel: ViewModel() {
    private val repository = DogRepository()
    private lateinit var classifierRepository: ClassifierRepository

    private val _dog = MutableLiveData<Dog>()
    val dog: LiveData<Dog>
        get() = _dog

    private val _status = MutableLiveData<ApiResponseStatus<Dog>>()
    val status: LiveData<ApiResponseStatus<Dog>>
        get() = _status

    private val _dogRecognition = MutableLiveData<DogRecognition>()
    val dogRecognition: LiveData<DogRecognition>
        get() = _dogRecognition

    fun setupClassifier(tfLiteModel: MappedByteBuffer, labels: List<String>){
        val classifier = Classifier(tfLiteModel, labels)
        classifierRepository = ClassifierRepository(classifier)
    }

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