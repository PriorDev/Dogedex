package com.prior_dev.dogedex.auth

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prior_dev.dogedex.R
import com.prior_dev.dogedex.api.ApiResponseStatus
import com.prior_dev.dogedex.models.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repository : AuthRepositoryTask
): ViewModel() {

    var user = mutableStateOf<User?>(null)
        private set

    var status = mutableStateOf<ApiResponseStatus<User>?>(null)
        private set

    var emailError = mutableStateOf<Int?>(null)
        private set

    var passwordError = mutableStateOf<Int?>(null)
        private set

    var passwordConfirmError = mutableStateOf<Int?>(null)
        private set

    fun signUp(
        email: String,
        password: String,
        passwordConfirmation: String,
    ){
        when {
            email.isEmpty() -> {
                emailError.value = R.string.email_no_empty
            }
            password.isEmpty() -> {
                passwordError.value = R.string.password_must_no_be_empty
            }
            passwordConfirmation.isEmpty() -> {
                passwordConfirmError.value = R.string.password_must_no_be_empty
            }
            password != passwordConfirmation -> {
                passwordConfirmError.value = R.string.passwords_does_not_match
            }
            else -> {
                viewModelScope.launch {
                    status.value = ApiResponseStatus.Loading()
                    val response = repository.signUp(email, password, passwordConfirmation)
                    handleResponseStatus(response)
                }
            }
        }
    }

    private fun handleResponseStatus(apiResponseStatus: ApiResponseStatus<User>) {
        if(apiResponseStatus is ApiResponseStatus.Success){
            user.value = apiResponseStatus.data
        }

        status.value = apiResponseStatus
    }

    fun login(email: String, password: String) {
        when {
            email.isEmpty() -> {
                emailError.value = R.string.email_no_empty
            }

            password.isEmpty() -> {
                passwordError.value = R.string.password_must_no_be_empty
            }

            else -> {
                viewModelScope.launch {
                    status.value = ApiResponseStatus.Loading()
                    val response = repository.login(email, password)
                    handleResponseStatus(response)
                }
            }
        }
    }

    fun resetApiResponseStatus(){
        status.value = null
    }

    fun resetErrors(){
        emailError.value = null
        passwordError.value = null
        passwordConfirmError.value = null
    }
}