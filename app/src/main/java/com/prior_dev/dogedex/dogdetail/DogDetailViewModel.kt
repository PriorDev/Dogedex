package com.prior_dev.dogedex.dogdetail

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prior_dev.dogedex.api.ApiResponseStatus
import com.prior_dev.dogedex.doglist.DogRepository
import kotlinx.coroutines.launch

class DogDetailViewModel: ViewModel() {
    private val repository = DogRepository()

    var status = mutableStateOf<ApiResponseStatus<Any>?>(null)
        private set


    fun addDotToUser(dogId: Long){
        viewModelScope.launch {
            status.value = ApiResponseStatus.Loading()
            val response = repository.addDogToUser(dogId)
            handleAddDogToUserStatus(response)
        }
    }

    private fun handleAddDogToUserStatus(apiResponseStatus: ApiResponseStatus<Any>){
        if(apiResponseStatus is ApiResponseStatus.Success){
            status.value = apiResponseStatus
        }
    }

    fun resetApiResponseStatus(){
        status.value = null
    }
}