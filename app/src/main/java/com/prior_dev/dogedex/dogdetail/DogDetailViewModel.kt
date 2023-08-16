package com.prior_dev.dogedex.dogdetail

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prior_dev.dogedex.api.ApiResponseStatus
import com.prior_dev.dogedex.dogdetail.DogDetailComposeActivity.Companion.DOG_KEY
import com.prior_dev.dogedex.dogdetail.DogDetailComposeActivity.Companion.IS_RECOGNITION_KEY
import com.prior_dev.dogedex.dogdetail.DogDetailComposeActivity.Companion.MOST_PROBABLE_DOGS_IDS
import com.prior_dev.dogedex.doglist.DogRepository
import com.prior_dev.dogedex.doglist.DogRepositoryTask
import com.prior_dev.dogedex.models.Dog
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DogDetailViewModel @Inject constructor(
    private val repository: DogRepositoryTask,
    savedStateHandle: SavedStateHandle,
): ViewModel() {
    var status = mutableStateOf<ApiResponseStatus<Any>?>(null)
        private set

    var dog = mutableStateOf(savedStateHandle.get<Dog>(DOG_KEY)!!)
        private set

    private var probableDogsIds = mutableStateOf(
        savedStateHandle.get<ArrayList<String>>(MOST_PROBABLE_DOGS_IDS) ?: arrayListOf()
    )

    var isRecognition = mutableStateOf(savedStateHandle.get<Boolean>(IS_RECOGNITION_KEY) ?: false)
        private set

    private val _probableDogList = MutableStateFlow<MutableList<Dog>>(mutableListOf())
    val probableDogList: StateFlow<MutableList<Dog>>
        get() = _probableDogList

    fun addDotToUser(){
        viewModelScope.launch {
            status.value = ApiResponseStatus.Loading()
            val response = repository.addDogToUser(dog.value.id)
            handleAddDogToUserStatus(response)
        }
    }

    fun getProbableDogs(){
        _probableDogList.value.clear()

        viewModelScope.launch {
            repository.getProbableDogs(probableDogsIds.value).collect{ response ->
                if(response is ApiResponseStatus.Success){
                    val probableDogMutableList = _probableDogList.value.toMutableList()
                    probableDogMutableList.add(response.data)
                    _probableDogList.value = probableDogMutableList
                }
            }
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

    fun updateDog(newDog: Dog){
         dog.value = newDog
    }

}