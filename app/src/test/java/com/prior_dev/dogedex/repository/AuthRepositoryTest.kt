package com.prior_dev.dogedex.repository

import com.prior_dev.dogedex.api.ApiResponseStatus
import com.prior_dev.dogedex.api.ApiService
import com.prior_dev.dogedex.api.dto.AddDotToUserDto
import com.prior_dev.dogedex.api.dto.LoginDto
import com.prior_dev.dogedex.api.dto.SignUpDto
import com.prior_dev.dogedex.api.dto.UserDto
import com.prior_dev.dogedex.api.response.AuthApiResponse
import com.prior_dev.dogedex.api.response.DogApiResponse
import com.prior_dev.dogedex.api.response.DogListApiResponse
import com.prior_dev.dogedex.api.response.UserApiResponse
import com.prior_dev.dogedex.R
import com.prior_dev.dogedex.auth.AuthRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class AuthRepositoryTest {
    private val fakeUserDto = UserDto(
        id = 1,
        email = "raul@test.com",
        authenticationToken = ""
    )

    @Test
    fun testSignUpSuccess(): Unit = runBlocking {
        class Service: ApiService{
            override suspend fun getAllDogs(): DogListApiResponse {
                TODO("Not yet implemented")
            }

            override suspend fun signUp(signUP: SignUpDto): AuthApiResponse {
                return AuthApiResponse(
                    message = "",
                    isSuccess = true,
                    data = UserApiResponse(fakeUserDto)
                )
            }

            override suspend fun login(loginDto: LoginDto): AuthApiResponse {
                TODO("Not yet implemented")
            }

            override suspend fun addDogToUser(addDotToUserDto: AddDotToUserDto): AuthApiResponse {
                TODO("Not yet implemented")
            }

            override suspend fun getUserDogs(): DogListApiResponse {
                TODO("Not yet implemented")
            }

            override suspend fun getDogByMlId(mlId: String): DogApiResponse {
                TODO("Not yet implemented")
            }
        }

        val repository = AuthRepository(Service())
        val response = repository.signUp("", "", "")
        assert(response is ApiResponseStatus.Success)
        val success = response as ApiResponseStatus.Success
        assertEquals(fakeUserDto.email, success.data.email)
    }

    @Test
    fun testSignUpError(): Unit = runBlocking {
        class Service: ApiService{
            override suspend fun getAllDogs(): DogListApiResponse {
                TODO("Not yet implemented")
            }

            override suspend fun signUp(signUP: SignUpDto): AuthApiResponse {
                return AuthApiResponse(
                    message = "",
                    isSuccess = false,
                    data = UserApiResponse(fakeUserDto)
                )
            }

            override suspend fun login(loginDto: LoginDto): AuthApiResponse {
                TODO("Not yet implemented")
            }

            override suspend fun addDogToUser(addDotToUserDto: AddDotToUserDto): AuthApiResponse {
                TODO("Not yet implemented")
            }

            override suspend fun getUserDogs(): DogListApiResponse {
                TODO("Not yet implemented")
            }

            override suspend fun getDogByMlId(mlId: String): DogApiResponse {
                TODO("Not yet implemented")
            }
        }

        val repository = AuthRepository(Service())
        val response = repository.signUp("", "", "")
        assert(response is ApiResponseStatus.Error)

        val error = response as ApiResponseStatus.Error
        assertEquals(R.string.error_undefine, error.messageId)
    }

    @Test
    fun testLoginSuccess(): Unit = runBlocking {
        class Service: ApiService{
            override suspend fun getAllDogs(): DogListApiResponse {
                TODO("Not yet implemented")
            }

            override suspend fun signUp(signUP: SignUpDto): AuthApiResponse {
                TODO("Not yet implemented")
            }

            override suspend fun login(loginDto: LoginDto): AuthApiResponse {
                return AuthApiResponse(
                    message = "",
                    isSuccess = true,
                    data = UserApiResponse(fakeUserDto)
                )
            }

            override suspend fun addDogToUser(addDotToUserDto: AddDotToUserDto): AuthApiResponse {
                TODO("Not yet implemented")
            }

            override suspend fun getUserDogs(): DogListApiResponse {
                TODO("Not yet implemented")
            }

            override suspend fun getDogByMlId(mlId: String): DogApiResponse {
                TODO("Not yet implemented")
            }
        }

        val repository = AuthRepository(Service())
        val response = repository.login("", "")
        assert(response is ApiResponseStatus.Success)

        val success = response as ApiResponseStatus.Success
        assertEquals(fakeUserDto.email, success.data.email)
    }

    @Test
    fun testLoginError(): Unit = runBlocking {
        class Service: ApiService{
            override suspend fun getAllDogs(): DogListApiResponse {
                TODO("Not yet implemented")
            }

            override suspend fun signUp(signUP: SignUpDto): AuthApiResponse {
                TODO("Not yet implemented")
            }

            override suspend fun login(loginDto: LoginDto): AuthApiResponse {
                return AuthApiResponse(
                    message = "",
                    isSuccess = false,
                    data = UserApiResponse(fakeUserDto)
                )
            }

            override suspend fun addDogToUser(addDotToUserDto: AddDotToUserDto): AuthApiResponse {
                TODO("Not yet implemented")
            }

            override suspend fun getUserDogs(): DogListApiResponse {
                TODO("Not yet implemented")
            }

            override suspend fun getDogByMlId(mlId: String): DogApiResponse {
                TODO("Not yet implemented")
            }
        }

        val repository = AuthRepository(Service())
        val response = repository.login("", "")
        assert(response is ApiResponseStatus.Error)

        val error = response as ApiResponseStatus.Error
        assertEquals(R.string.error_undefine, error.messageId)
    }
}