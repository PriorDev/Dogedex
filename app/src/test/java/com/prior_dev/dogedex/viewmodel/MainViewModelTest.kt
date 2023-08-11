package com.prior_dev.dogedex.viewmodel

import androidx.camera.core.ImageProxy
import com.prior_dev.dogedex.DogedexCoroutineRule
import com.prior_dev.dogedex.api.ApiResponseStatus
import com.prior_dev.dogedex.doglist.DogRepositoryTask
import com.prior_dev.dogedex.machinglearning.ClassifierRepositoryTask
import com.prior_dev.dogedex.machinglearning.DogRecognition
import com.prior_dev.dogedex.main.MainViewModel
import com.prior_dev.dogedex.models.Dog
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

//TODO: Corregir este test
class MainViewModelTest {
    @get:Rule
    val dogedexCoroutineRule = DogedexCoroutineRule()

    @Test
    fun TestGetRecognizedDogIsSuccess(){
        val fakeDog = Dog(1,1)
        val fakeDogRecognition = DogRecognition("1", 50.25f)
        class FakeDogRepo: DogRepositoryTask{
            override suspend fun getDogCollection(): ApiResponseStatus<List<Dog>> {
                TODO("Not yet implemented")
            }

            override suspend fun addDogToUser(dogId: Long): ApiResponseStatus<Any> {
                TODO("Not yet implemented")
            }

            override suspend fun getDogByMlId(mlDogId: String): ApiResponseStatus<Dog> {
                return ApiResponseStatus.Success(fakeDog)
            }
        }

        class FakeClassifierRepo: ClassifierRepositoryTask{
            override suspend fun recognizeImage(imageProxy: ImageProxy): DogRecognition {
                return fakeDogRecognition
            }
        }

        val viewModel = MainViewModel(FakeDogRepo(), FakeClassifierRepo())

        viewModel.getRecognizedDog(fakeDogRecognition.id)

        assertEquals(fakeDog.id, viewModel.dog.value!!.id)
        assert(viewModel.status.value is ApiResponseStatus.Success)
    }

    @Test
    fun TestGetRecognizedDogIsError(){
        val fakeDog = Dog(1,1)
        val fakeDogRecognition = DogRecognition("1", 50.25f)
        class FakeDogRepo: DogRepositoryTask{
            override suspend fun getDogCollection(): ApiResponseStatus<List<Dog>> {
                TODO("Not yet implemented")
            }

            override suspend fun addDogToUser(dogId: Long): ApiResponseStatus<Any> {
                TODO("Not yet implemented")
            }

            override suspend fun getDogByMlId(mlDogId: String): ApiResponseStatus<Dog> {
                return ApiResponseStatus.Error(1)
            }
        }

        class FakeClassifierRepo: ClassifierRepositoryTask{
            override suspend fun recognizeImage(imageProxy: ImageProxy): DogRecognition {
                return fakeDogRecognition
            }
        }

        val viewModel = MainViewModel(FakeDogRepo(), FakeClassifierRepo())

        viewModel.getRecognizedDog(fakeDogRecognition.id)

        assert(viewModel.status.value is ApiResponseStatus.Error)
    }
}