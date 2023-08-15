package com.prior_dev.dogedex.viewmodel

import androidx.camera.core.ImageProxy
import com.prior_dev.dogedex.DogedexCoroutineRule
import com.prior_dev.dogedex.api.ApiResponseStatus
import com.prior_dev.dogedex.auth.UserRepositoryTask
import com.prior_dev.dogedex.doglist.DogRepositoryTask
import com.prior_dev.dogedex.machinglearning.ClassifierRepositoryTask
import com.prior_dev.dogedex.machinglearning.DogRecognition
import com.prior_dev.dogedex.main.MainViewModel
import com.prior_dev.dogedex.models.Dog
import com.prior_dev.dogedex.models.User
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

//TODO: Corregir este test
class MainViewModelTest {
    @get:Rule
    var dogedexCoroutineRule = DogedexCoroutineRule()

    class FakeUserRepo: UserRepositoryTask {
        override fun setLoggedInUser(user: User) {

        }

        override fun getLoggedInUser(): User {
            return User(1, "raul@test.com", "hdkjfjlkd")
        }

        override fun logout() {

        }
    }

//    @Test
//    fun testGetRecognizedDogIsSuccess() {
//        val fakeDog = Dog(1,1)
//        val fakeDogRecognition = DogRecognition("1", 50.25f)
//        class FakeDogRepo: DogRepositoryTask{
//            override suspend fun getDogCollection(): ApiResponseStatus<List<Dog>> {
//                return ApiResponseStatus.Success(emptyList())
//            }
//
//            override suspend fun addDogToUser(dogId: Long): ApiResponseStatus<Any> {
//                return ApiResponseStatus.Success(dogId)
//            }
//
//            override suspend fun getDogByMlId(mlDogId: String): ApiResponseStatus<Dog> {
//                return ApiResponseStatus.Success(fakeDog)
//            }
//        }
//
//        class FakeClassifierRepo: ClassifierRepositoryTask{
//            override suspend fun recognizeImage(imageProxy: ImageProxy): DogRecognition {
//                return fakeDogRecognition
//            }
//        }
//
//        val viewModel = MainViewModel(FakeDogRepo(), FakeClassifierRepo(), FakeUserRepo())
//
//        viewModel.getRecognizedDog(fakeDogRecognition.id)
//
//        assert(viewModel.status.value is ApiResponseStatus.Success)
//        assertEquals(fakeDog.id, viewModel.dog.value!!.id)
//    }
//
//    @Test
//    fun testGetRecognizedDogIsError(){
//        val fakeDogRecognition = DogRecognition("1", 50.25f)
//        class FakeDogRepo: DogRepositoryTask{
//            override suspend fun getDogCollection(): ApiResponseStatus<List<Dog>> {
//                return ApiResponseStatus.Error(1)
//            }
//
//            override suspend fun addDogToUser(dogId: Long): ApiResponseStatus<Any> {
//                return ApiResponseStatus.Error(1)
//            }
//
//            override suspend fun getDogByMlId(mlDogId: String): ApiResponseStatus<Dog> {
//                return ApiResponseStatus.Error(1)
//            }
//        }
//
//        class FakeClassifierRepo: ClassifierRepositoryTask{
//            override suspend fun recognizeImage(imageProxy: ImageProxy): DogRecognition {
//                return fakeDogRecognition
//            }
//        }
//
//        val viewModel = MainViewModel(FakeDogRepo(), FakeClassifierRepo(), FakeUserRepo())
//
//        viewModel.getRecognizedDog(fakeDogRecognition.id)
//
//        assert(viewModel.status.value is ApiResponseStatus.Error)
//    }
}