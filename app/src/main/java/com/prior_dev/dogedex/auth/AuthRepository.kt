package com.prior_dev.dogedex.auth

import com.prior_dev.dogedex.api.ApiResponseStatus
import com.prior_dev.dogedex.api.DogsApi
import com.prior_dev.dogedex.api.dto.LoginDto
import com.prior_dev.dogedex.api.dto.SignUpDto
import com.prior_dev.dogedex.api.makeNetworkCall
import com.prior_dev.dogedex.models.User
import com.prior_dev.dogedex.models.toDomain

class AuthRepository {
    suspend fun signUp(
        email: String,
        password: String,
        passwordConfirmation: String,
    ): ApiResponseStatus<User> {
        return makeNetworkCall {
            val signUpDto = SignUpDto(email, password, passwordConfirmation)
            val response = DogsApi.retrofitService.signUp(signUpDto)

            if(!response.isSuccess){
                throw Exception(response.message)
            }

            response.data.user.toDomain()
        }
    }

    suspend fun login(
        email: String,
        password: String,
    ): ApiResponseStatus<User> {
        return makeNetworkCall {
            val loginDto = LoginDto(email, password)
            val response = DogsApi.retrofitService.login(loginDto)

            if(!response.isSuccess){
                throw Exception(response.message)
            }

            response.data.user.toDomain()
        }
    }
}