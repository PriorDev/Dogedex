package com.prior_dev.dogedex.repository

import com.prior_dev.dogedex.api.ApiResponseStatus
import com.prior_dev.dogedex.api.ApiService
import com.prior_dev.dogedex.api.dto.AddDotToUserDto
import com.prior_dev.dogedex.api.dto.DogDto
import com.prior_dev.dogedex.api.dto.LoginDto
import com.prior_dev.dogedex.api.dto.SignUpDto
import com.prior_dev.dogedex.api.response.AuthApiResponse
import com.prior_dev.dogedex.api.response.DogApiResponse
import com.prior_dev.dogedex.api.response.DogListApiResponse
import com.prior_dev.dogedex.api.response.DogListResponse
import com.prior_dev.dogedex.doglist.DogRepository
import com.prior_dev.dogedex.R
import com.prior_dev.dogedex.api.response.DogResponse
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import org.junit.Assert.assertEquals
import org.junit.Test
import java.net.UnknownHostException

class DogRepositoryTest {

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun testGetDogCollectionSuccess() : Unit = runBlocking {
        class Service: ApiService{
            override suspend fun getAllDogs(): DogListApiResponse {
                return DogListApiResponse(
                    message = "",
                    isSuccess = true,
                    data = DogListResponse(
                        listOf(
                            DogDto(1, "", "", "", 1,
                                "", "", "Chihuahua", "", "",
                                ""),
                            DogDto(19, "", "", "", 19,
                                "", "", "Pastor Aleman", "",
                                "", ""),
                        )
                    )
                )
            }

            override suspend fun signUp(signUP: SignUpDto): AuthApiResponse {
                TODO("Not yet implemented")
            }

            override suspend fun login(loginDto: LoginDto): AuthApiResponse {
                TODO("Not yet implemented")
            }

            override suspend fun addDogToUser(addDotToUserDto: AddDotToUserDto): AuthApiResponse {
                TODO("Not yet implemented")
            }

            override suspend fun getUserDogs(): DogListApiResponse {
                return DogListApiResponse(
                    message = "",
                    isSuccess = true,
                    data = DogListResponse(
                        listOf(
                            DogDto(19, "", "", "", 19,
                                "", "", "Pastor Aleman", "",
                                "", ""),
                        )
                    )
                )
            }

            override suspend fun getDogByMlId(mlId: String): DogApiResponse {
                TODO("Not yet implemented")
            }

        }

        val dogRepo = DogRepository(
            api = Service(),
            dispatcher = TestCoroutineDispatcher()
        )

        val apiResponseStatus = dogRepo.getDogCollection()

        assert(apiResponseStatus is ApiResponseStatus.Success)

        val dogCollection = (apiResponseStatus as ApiResponseStatus.Success).data
        assertEquals(2, dogCollection.size)
        assertEquals(1, dogCollection.filter { it.inCollection  }.size)
        assertEquals(1, dogCollection.filter { !it.inCollection  }.size)

    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun testGetDogCollectionError() : Unit = runBlocking {
        class Service: ApiService{
            override suspend fun getAllDogs(): DogListApiResponse {
                throw UnknownHostException()
            }

            override suspend fun signUp(signUP: SignUpDto): AuthApiResponse {
                TODO("Not yet implemented")
            }

            override suspend fun login(loginDto: LoginDto): AuthApiResponse {
                TODO("Not yet implemented")
            }

            override suspend fun addDogToUser(addDotToUserDto: AddDotToUserDto): AuthApiResponse {
                TODO("Not yet implemented")
            }

            override suspend fun getUserDogs(): DogListApiResponse {
                return DogListApiResponse(
                    message = "",
                    isSuccess = true,
                    data = DogListResponse(
                        listOf(
                            DogDto(19, "", "", "", 19,
                                "", "", "Pastor Aleman", "",
                                "", ""),
                        )
                    )
                )
            }

            override suspend fun getDogByMlId(mlId: String): DogApiResponse {
                TODO("Not yet implemented")
            }

        }

        val dogRepo = DogRepository(
            api = Service(),
            dispatcher = TestCoroutineDispatcher()
        )

        val apiResponseStatus = dogRepo.getDogCollection()

        assert(apiResponseStatus is ApiResponseStatus.Error)

        val error = (apiResponseStatus as ApiResponseStatus.Error)

        assertEquals(R.string.unkown_host, error.messageId)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun testGetDogByMLSuccess() : Unit = runBlocking {
        val dogDto = DogDto(19, "", "", "", 19,
            "", "", "Pastor Aleman", "",
            "", "")
        class Service: ApiService{
            override suspend fun getAllDogs(): DogListApiResponse {
                throw UnknownHostException()
            }

            override suspend fun signUp(signUP: SignUpDto): AuthApiResponse {
                TODO("Not yet implemented")
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
                return DogApiResponse(
                    message = "",
                    isSuccess = true,
                    data = DogResponse(dogDto)
                )
            }

        }

        //TODO: Corregir los elementos deprecrados
        val dogRepo = DogRepository(
            api = Service(),
            dispatcher = TestCoroutineDispatcher()
        )

        val apiResponseStatus = dogRepo.getDogByMlId("df")

        assert(apiResponseStatus is ApiResponseStatus.Success)

        val success = (apiResponseStatus as ApiResponseStatus.Success)

        assertEquals(dogDto.id, success.data.id)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun testGetDogByMLError() : Unit = runBlocking {
        val dogDto = DogDto(19, "", "", "", 19,
            "", "", "Pastor Aleman", "",
            "", "")
        class Service: ApiService{
            override suspend fun getAllDogs(): DogListApiResponse {
                throw UnknownHostException()
            }

            override suspend fun signUp(signUP: SignUpDto): AuthApiResponse {
                TODO("Not yet implemented")
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
                return DogApiResponse(
                    message = "Error_getting_ML",
                    isSuccess = false,
                    data = DogResponse(dogDto)
                )
            }

        }

        //TODO: Corregir los elementos deprecrados
        val dogRepo = DogRepository(
            api = Service(),
            dispatcher = TestCoroutineDispatcher()
        )

        val apiResponseStatus = dogRepo.getDogByMlId("df")

        assert(apiResponseStatus is ApiResponseStatus.Error)

        val error = (apiResponseStatus as ApiResponseStatus.Error)

        assertEquals(R.string.error_undefine, error.messageId)
    }
}