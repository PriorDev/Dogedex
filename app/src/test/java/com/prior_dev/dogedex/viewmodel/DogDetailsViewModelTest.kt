package com.prior_dev.dogedex.viewmodel

import com.prior_dev.dogedex.DogedexCoroutineRule
import com.prior_dev.dogedex.api.ApiResponseStatus
import com.prior_dev.dogedex.dogdetail.DogDetailViewModel
import com.prior_dev.dogedex.doglist.DogRepositoryTask
import com.prior_dev.dogedex.models.Dog
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class DogDetailsViewModelTest {
    @get:Rule
    val dogedexCoroutineRule = DogedexCoroutineRule()

    @Test
    fun TestAddDotToUserIsSuccess(){
        class FakeDogRepo: DogRepositoryTask{
            override suspend fun getDogCollection(): ApiResponseStatus<List<Dog>> {
                TODO("Not yet implemented")
            }

            override suspend fun addDogToUser(dogId: Long): ApiResponseStatus<Any> {
                return ApiResponseStatus.Success(1)
            }

            override suspend fun getDogByMlId(mlDogId: String): ApiResponseStatus<Dog> {
                TODO("Not yet implemented")
            }
        }

        val viewModel = DogDetailViewModel(FakeDogRepo())
        viewModel.addDotToUser(1L)

        assert(viewModel.status.value is ApiResponseStatus.Success)
    }

    @Test
    fun TestResetApiResponse(){
        class FakeDogRepo: DogRepositoryTask{
            override suspend fun getDogCollection(): ApiResponseStatus<List<Dog>> {
                TODO("Not yet implemented")
            }

            override suspend fun addDogToUser(dogId: Long): ApiResponseStatus<Any> {
                return ApiResponseStatus.Success(1)
            }

            override suspend fun getDogByMlId(mlDogId: String): ApiResponseStatus<Dog> {
                TODO("Not yet implemented")
            }
        }

        val viewModel = DogDetailViewModel(FakeDogRepo())
        viewModel.addDotToUser(1L)
        viewModel.resetApiResponseStatus()

        assertEquals(null, viewModel.status.value)
    }
}