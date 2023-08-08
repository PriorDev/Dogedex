package com.prior_dev.dogedex.dogdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prior_dev.dogedex.api.ApiResponseStatus
import com.prior_dev.dogedex.doglist.DogRepository
import kotlinx.coroutines.launch

class DogDetailViewModel: ViewModel() {
    private val repository = DogRepository()

    private val _status = MutableLiveData<ApiResponseStatus<Any>>()
    val status: LiveData<ApiResponseStatus<Any>>
        get() = _status

    fun addDotToUser(dogId: Long){
        viewModelScope.launch {
            _status.value = ApiResponseStatus.Loading()
            val response = repository.addDogToUser(dogId)
            handleAddDogToUserStatus(response)
        }
    }

    private fun handleAddDogToUserStatus(apiResponseStatus: ApiResponseStatus<Any>){
        if(apiResponseStatus is ApiResponseStatus.Success){

        }
    }
}