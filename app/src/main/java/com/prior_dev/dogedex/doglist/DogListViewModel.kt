package com.prior_dev.dogedex.doglist

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prior_dev.dogedex.models.Dog
import com.prior_dev.dogedex.api.ApiResponseStatus
import kotlinx.coroutines.launch

class DogListViewModel: ViewModel() {
    private val repository = DogRepository()

    var dogList = mutableStateOf<List<Dog>>(listOf())
        private set

    var status = mutableStateOf<ApiResponseStatus<Any>?>(null)
        private set

    init {
        getDogCollection()
    }

    private fun getDogCollection() {
        viewModelScope.launch {
            status.value = ApiResponseStatus.Loading()
            handleResponseStatus(repository.getDogCollection())
        }
    }

    @Suppress("UNCHECKED_CAST")
    private fun handleResponseStatus(apiResponseStatus: ApiResponseStatus<List<Dog>>) {
        if(apiResponseStatus is ApiResponseStatus.Success){
            dogList.value = apiResponseStatus.data!!
        }
        status.value = apiResponseStatus as ApiResponseStatus<Any>
    }

    fun resetApiResponseStatus(){
        status.value = null
    }
}