package com.prior_dev.dogedex.doglist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prior_dev.dogedex.models.Dog
import com.prior_dev.dogedex.api.ApiResponseStatus
import kotlinx.coroutines.launch

class DogListViewModel: ViewModel() {
    private val repository = DogRepository()

    private val _dogList = MutableLiveData<List<Dog>>()
    val dogList: LiveData<List<Dog>>
        get() = _dogList

    private val _status = MutableLiveData<ApiResponseStatus<List<Dog>>>()
    val status: LiveData<ApiResponseStatus<List<Dog>>>
        get() = _status

    init {
        downloadDogs()
    }

    private fun downloadDogs() {
        viewModelScope.launch {
            _status.value = ApiResponseStatus.Loading()
            handleResponseStatus(repository.downloadDogs())
        }
    }

    private fun handleResponseStatus(apiResponseStatus: ApiResponseStatus<List<Dog>>) {
        if(apiResponseStatus is ApiResponseStatus.Success){
            _dogList.value = apiResponseStatus.data ?: emptyList()
        }
        _status.value = apiResponseStatus
    }
}