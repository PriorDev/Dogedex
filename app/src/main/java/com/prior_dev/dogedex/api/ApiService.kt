package com.prior_dev.dogedex.api

import com.prior_dev.dogedex.ADD_DOG_TO_USER_URL
import com.prior_dev.dogedex.BASE_URL
import com.prior_dev.dogedex.GET_ALL_DOGS_URL
import com.prior_dev.dogedex.GET_DOG_ML_ID
import com.prior_dev.dogedex.GET_USER_DOGS_URL
import com.prior_dev.dogedex.LOGIN_URL
import com.prior_dev.dogedex.SIGN_UP_URL
import com.prior_dev.dogedex.api.dto.AddDotToUserDto
import com.prior_dev.dogedex.api.dto.LoginDto
import com.prior_dev.dogedex.api.dto.SignUpDto
import com.prior_dev.dogedex.api.response.DogListApiResponse
import com.prior_dev.dogedex.api.response.AuthApiResponse
import com.prior_dev.dogedex.api.response.DogApiResponse
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

private val okHttpClient = OkHttpClient
    .Builder()
    .addInterceptor(ApiServiceInterceptor)
    .build()


private val retrofit = Retrofit.Builder()
    .client(okHttpClient)
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

    @Headers("${ApiServiceInterceptor.NEEDS_AUTH_HEADER_KEY}: true")
    @POST(ADD_DOG_TO_USER_URL)
    suspend fun addDogToUser(@Body addDotToUserDto: AddDotToUserDto): AuthApiResponse

    @Headers("${ApiServiceInterceptor.NEEDS_AUTH_HEADER_KEY}: true")
    @GET(GET_USER_DOGS_URL)
    suspend fun getUserDogs(): DogListApiResponse

    @GET(GET_DOG_ML_ID)
    suspend fun getDogByMlId(@Query("ml_id") mlId: String): DogApiResponse
}

object DogsApi{
    val retrofitService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}
