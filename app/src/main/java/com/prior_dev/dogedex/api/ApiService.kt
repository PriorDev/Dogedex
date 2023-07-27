package com.prior_dev.dogedex.api

import com.prior_dev.dogedex.BASE_URL
import com.prior_dev.dogedex.Dog
import com.prior_dev.dogedex.GET_ALL_DOGS
import com.prior_dev.dogedex.api.response.DogListApiResponse
import com.prior_dev.dogedex.api.response.DogListResponse
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

private val retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(MoshiConverterFactory.create())
    .build()

interface ApiService{
    @GET(GET_ALL_DOGS)
    suspend fun getAllDogs(): DogListApiResponse
}

object DogsApi{
    val retrofitService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}
