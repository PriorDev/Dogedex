package com.prior_dev.dogedex.auth

import com.prior_dev.dogedex.api.ApiResponseStatus
import com.prior_dev.dogedex.api.ApiService
import com.prior_dev.dogedex.api.dto.LoginDto
import com.prior_dev.dogedex.api.dto.SignUpDto
import com.prior_dev.dogedex.api.makeNetworkCall
import com.prior_dev.dogedex.models.User
import com.prior_dev.dogedex.models.toDomain
import javax.inject.Inject

interface AuthRepositoryTask{
    suspend fun signUp(
        email: String,
        password: String,
        passwordConfirmation: String,
    ): ApiResponseStatus<User>

    suspend fun login(
        email: String,
        password: String,
    ): ApiResponseStatus<User>
}

class AuthRepository @Inject constructor(
    private val api: ApiService
): AuthRepositoryTask {
    override suspend fun signUp(
        email: String,
        password: String,
        passwordConfirmation: String,
    ): ApiResponseStatus<User> {
        return makeNetworkCall {
            val signUpDto = SignUpDto(email, password, passwordConfirmation)
            val response = api.signUp(signUpDto)

            if(!response.isSuccess){
                throw Exception(response.message)
            }

            response.data.user.toDomain()
        }
    }

    override suspend fun login(
        email: String,
        password: String,
    ): ApiResponseStatus<User> {
        return makeNetworkCall {
            val loginDto = LoginDto(email, password)
            val response = api.login(loginDto)

            if(!response.isSuccess){
                throw Exception(response.message)
            }

            response.data.user.toDomain()
        }
    }
}