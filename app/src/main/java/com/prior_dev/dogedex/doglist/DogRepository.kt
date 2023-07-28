package com.prior_dev.dogedex.doglist

import com.prior_dev.dogedex.models.Dog
import com.prior_dev.dogedex.api.ApiResponseStatus
import com.prior_dev.dogedex.api.DogsApi
import com.prior_dev.dogedex.api.makeNetworkCall
import com.prior_dev.dogedex.api.dto.toDomain

class DogRepository {
    suspend fun downloadDogs(): ApiResponseStatus<List<Dog>>{
        return makeNetworkCall {
            val response = DogsApi.retrofitService.getAllDogs()
            response.data.dogs.map { it.toDomain() }
        }
    }
}