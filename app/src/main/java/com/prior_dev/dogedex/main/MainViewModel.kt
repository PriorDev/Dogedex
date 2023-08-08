package com.prior_dev.dogedex.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prior_dev.dogedex.api.ApiResponseStatus
import com.prior_dev.dogedex.doglist.DogRepository
import com.prior_dev.dogedex.models.Dog
import kotlinx.coroutines.launch

class MainViewModel: ViewModel() {
    private val _dog = MutableLiveData<Dog>()
    val dog: LiveData<Dog>
        get() = _dog

    private val _status = MutableLiveData<ApiResponseStatus<Dog>>()
    val status: LiveData<ApiResponseStatus<Dog>>
        get() = _status

    private val repository = DogRepository()


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
}