package com.prior_dev.dogedex.api

import com.prior_dev.dogedex.BASE_URL
import com.prior_dev.dogedex.GET_ALL_DOGS_URL
import com.prior_dev.dogedex.LOGIN_URL
import com.prior_dev.dogedex.SIGN_UP_URL
import com.prior_dev.dogedex.api.dto.LoginDto
import com.prior_dev.dogedex.api.dto.SignUpDto
import com.prior_dev.dogedex.api.response.DogListApiResponse
import com.prior_dev.dogedex.api.response.AuthApiResponse
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

private val retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(MoshiConverterFactory.create())
    .build()

interface ApiService{
    @GET(GET_ALL_DOGS_URL)
    suspend fun getAllDogs(): DogListApiResponse

    @POST(SIGN_UP_URL)
    suspend fun signUp(@Body signUP: SignUpDto): AuthApiResponse

    @POST(LOGIN_URL)
    suspend fun login(@Body loginDto: LoginDto): AuthApiResponse
}

object DogsApi{
    val retrofitService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}
