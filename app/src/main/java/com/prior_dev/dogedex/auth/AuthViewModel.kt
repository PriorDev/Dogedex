package com.prior_dev.dogedex.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prior_dev.dogedex.api.ApiResponseStatus
import com.prior_dev.dogedex.models.User
import kotlinx.coroutines.launch

class AuthViewModel: ViewModel() {
    private val repository = AuthRepository()

    private val _user = MutableLiveData<User>()
    val user: LiveData<User>
        get() = _user

    private val _status = MutableLiveData<ApiResponseStatus<User>>()
    val status: LiveData<ApiResponseStatus<User>>
        get() = _status

    fun signUp(
        email: String,
        password: String,
        passwordConfirmation: String,
    ){
        viewModelScope.launch {
            _status.value = ApiResponseStatus.Loading()
            val response = repository.signUp(email, password, passwordConfirmation)
            handleResponseStatus(response)
        }
    }

    private fun handleResponseStatus(apiResponseStatus: ApiResponseStatus<User>) {
        if(apiResponseStatus is ApiResponseStatus.Success){
            _user.value = apiResponseStatus.data!!
        }
        _status.value = apiResponseStatus
    }

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _status.value = ApiResponseStatus.Loading()
            val respones = repository.login(email, password)
            handleResponseStatus(respones)
        }
    }
}