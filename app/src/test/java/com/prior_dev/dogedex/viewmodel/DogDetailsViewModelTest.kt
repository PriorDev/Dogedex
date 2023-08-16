package com.prior_dev.dogedex.viewmodel

import androidx.lifecycle.SavedStateHandle
import com.prior_dev.dogedex.DogedexCoroutineRule
import com.prior_dev.dogedex.api.ApiResponseStatus
import com.prior_dev.dogedex.dogdetail.DogDetailComposeActivity
import com.prior_dev.dogedex.dogdetail.DogDetailViewModel
import com.prior_dev.dogedex.doglist.DogRepositoryTask
import com.prior_dev.dogedex.models.Dog
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class DogDetailsViewModelTest {
    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val dogedexCoroutineRule = DogedexCoroutineRule()

    @Test
    fun testAddDotToUserIsSuccess(){
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

            override suspend fun getProbableDogs(probableDogsId: ArrayList<String>): Flow<ApiResponseStatus<Dog>> {
                TODO("Not yet implemented")
            }
        }

        val savedStateHandle = SavedStateHandle()
        savedStateHandle[DogDetailComposeActivity.DOG_KEY] = Dog(1, 100)
        savedStateHandle[DogDetailComposeActivity.MOST_PROBABLE_DOGS_IDS] = arrayListOf<String>()
        savedStateHandle[DogDetailComposeActivity.IS_RECOGNITION_KEY] = true

        val viewModel = DogDetailViewModel(FakeDogRepo(), savedStateHandle)
        viewModel.addDotToUser()

        assert(viewModel.status.value is ApiResponseStatus.Success)
    }

    @Test
    fun testResetApiResponse(){
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

            override suspend fun getProbableDogs(probableDogsId: ArrayList<String>): Flow<ApiResponseStatus<Dog>> {
                TODO("Not yet implemented")
            }
        }

        val savedStateHandle = SavedStateHandle()
        savedStateHandle[DogDetailComposeActivity.DOG_KEY] = Dog(1, 100)
        savedStateHandle[DogDetailComposeActivity.MOST_PROBABLE_DOGS_IDS] = arrayListOf<String>()
        savedStateHandle[DogDetailComposeActivity.IS_RECOGNITION_KEY] = true

        val viewModel = DogDetailViewModel(FakeDogRepo(), savedStateHandle)
        viewModel.addDotToUser()
        viewModel.resetApiResponseStatus()

        assertEquals(null, viewModel.status.value)
    }
}