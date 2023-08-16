package com.prior_dev.dogedex.viewmodel

import com.prior_dev.dogedex.DogedexCoroutineRule
import com.prior_dev.dogedex.api.ApiResponseStatus
import com.prior_dev.dogedex.doglist.DogListViewModel
import com.prior_dev.dogedex.doglist.DogRepositoryTask
import com.prior_dev.dogedex.models.Dog
import kotlinx.coroutines.flow.Flow
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class DogListViewModelTest {
    @get:Rule
    var dogedexCoroutineRule = DogedexCoroutineRule()

    @Test
    fun downloadDogListStatusIsCorrect(){
        class fakeDogRepository : DogRepositoryTask{
            override suspend fun getDogCollection(): ApiResponseStatus<List<Dog>> {
                return ApiResponseStatus.Success(
                    listOf(
                        Dog(id = 1, index = 1),
                        Dog(id = 19, index = 2)
                    )
                )
            }

            override suspend fun addDogToUser(dogId: Long): ApiResponseStatus<Any> {
                return ApiResponseStatus.Success(Unit)
            }

            override suspend fun getDogByMlId(mlDogId: String): ApiResponseStatus<Dog> {
                return ApiResponseStatus.Success(Dog(id = 2, index = 2))
            }

            override suspend fun getProbableDogs(probableDogsId: ArrayList<String>): Flow<ApiResponseStatus<Dog>> {
                TODO("Not yet implemented")
            }

        }

        val viewModel = DogListViewModel(fakeDogRepository())
        assertEquals(2, viewModel.dogList.value.size)
        assertEquals(19, viewModel.dogList.value[1].id)
        assert(viewModel.status.value is ApiResponseStatus.Success)
    }

    @Test
    fun downloadDogListStatusIsError(){
        class fakeDogRepository : DogRepositoryTask{
            override suspend fun getDogCollection(): ApiResponseStatus<List<Dog>> {
                return ApiResponseStatus.Error(messageId = 12)
            }

            override suspend fun addDogToUser(dogId: Long): ApiResponseStatus<Any> {
                return ApiResponseStatus.Success(Unit)
            }

            override suspend fun getDogByMlId(mlDogId: String): ApiResponseStatus<Dog> {
                return ApiResponseStatus.Success(Dog(id = 2, index = 2))
            }

            override suspend fun getProbableDogs(probableDogsId: ArrayList<String>): Flow<ApiResponseStatus<Dog>> {
                TODO("Not yet implemented")
            }

        }

        val viewModel = DogListViewModel(fakeDogRepository())
        assertEquals(0, viewModel.dogList.value.size)
        assert(viewModel.status.value is ApiResponseStatus.Error)
    }

    @Test
    fun resetStatusIsClean(){
        class fakeDogRepository : DogRepositoryTask{
            override suspend fun getDogCollection(): ApiResponseStatus<List<Dog>> {
                return ApiResponseStatus.Error(messageId = 12)
            }

            override suspend fun addDogToUser(dogId: Long): ApiResponseStatus<Any> {
                return ApiResponseStatus.Success(Unit)
            }

            override suspend fun getDogByMlId(mlDogId: String): ApiResponseStatus<Dog> {
                return ApiResponseStatus.Success(Dog(id = 2, index = 2))
            }

            override suspend fun getProbableDogs(probableDogsId: ArrayList<String>): Flow<ApiResponseStatus<Dog>> {
                TODO("Not yet implemented")
            }

        }

        val viewModel = DogListViewModel(fakeDogRepository())

        assert(viewModel.status.value is ApiResponseStatus.Error)
        viewModel.resetApiResponseStatus()
        assert(viewModel.status.value == null)
    }
}