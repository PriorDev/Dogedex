package com.prior_dev.dogedex.doglist

import com.prior_dev.dogedex.R
import com.prior_dev.dogedex.api.ApiResponseStatus
import com.prior_dev.dogedex.api.ApiService
import com.prior_dev.dogedex.api.dto.AddDotToUserDto
import com.prior_dev.dogedex.api.dto.toDomain
import com.prior_dev.dogedex.api.makeNetworkCall
import com.prior_dev.dogedex.models.Dog
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface DogRepositoryTask {
    suspend fun getDogCollection(): ApiResponseStatus<List<Dog>>
    suspend fun addDogToUser(dogId: Long): ApiResponseStatus<Any>
    suspend fun getDogByMlId(mlDogId: String): ApiResponseStatus<Dog>
}

class DogRepository @Inject constructor(
    private val api: ApiService,
    private val dispatcher: CoroutineDispatcher
) : DogRepositoryTask {
    override suspend fun getDogCollection(): ApiResponseStatus<List<Dog>>{
        return withContext(dispatcher){
            val allDogsListJob = async { downloadDogs() }
            val userDogsListJob = async { getUserDogs() }

            val allDogsListResponse = allDogsListJob.await()
            val userDogsListResponse = userDogsListJob.await()

            if(allDogsListResponse is ApiResponseStatus.Error){
                allDogsListResponse
            }else if(userDogsListResponse is ApiResponseStatus.Error){
                userDogsListResponse
            }else if(
                allDogsListResponse is ApiResponseStatus.Success &&
                userDogsListResponse is ApiResponseStatus.Success
            ){
                ApiResponseStatus.Success(
                    mergeDogCollection(allDogsListResponse.data, userDogsListResponse.data)
                )
            }else{
                ApiResponseStatus.Error(R.string.error_undefine)
            }
        }
    }

    private fun mergeDogCollection(allDogsList: List<Dog>, userDogList: List<Dog>): List<Dog>{
        return allDogsList.map {
            if(userDogList.contains(it)){
                it
            }else{
                Dog(
                    index = it.index,
                    inCollection = false
                )
            }
        }.sortedBy { it.index }
    }

    private suspend fun downloadDogs(): ApiResponseStatus<List<Dog>>{
        return makeNetworkCall {
            val response = api.getAllDogs()
            response.data.dogs.map { it.toDomain() }
        }
    }

    private suspend fun getUserDogs(): ApiResponseStatus<List<Dog>>{
        return makeNetworkCall {
            val response = api.getUserDogs()
            response.data.dogs.map { it.toDomain() }
        }
    }

    override suspend fun addDogToUser(dogId: Long): ApiResponseStatus<Any>{
        return makeNetworkCall {
            val addDotToUserDto = AddDotToUserDto(dogId)
            val response = api.addDogToUser(addDotToUserDto)

            if(!response.isSuccess){
                throw Exception(response.message)
            }
        }
    }

    override suspend fun getDogByMlId(mlDogId: String): ApiResponseStatus<Dog>{
        return makeNetworkCall {
            val response = api.getDogByMlId(mlDogId)

            if(!response.isSuccess){
                throw Exception(response.message)
            }

            response.data.dog.toDomain()
        }
    }
}