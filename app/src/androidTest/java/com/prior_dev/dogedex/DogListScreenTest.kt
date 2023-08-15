package com.prior_dev.dogedex

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import com.prior_dev.dogedex.api.ApiResponseStatus
import com.prior_dev.dogedex.doglist.DogListView
import com.prior_dev.dogedex.doglist.DogListViewModel
import com.prior_dev.dogedex.doglist.DogRepositoryTask
import com.prior_dev.dogedex.models.Dog
import org.junit.Rule
import org.junit.Test

class DogListScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun progressBarShowsWhenLoadingState(){
        class DogRepo: DogRepositoryTask{
            override suspend fun getDogCollection(): ApiResponseStatus<List<Dog>> {
                return ApiResponseStatus.Loading()
            }

            override suspend fun addDogToUser(dogId: Long): ApiResponseStatus<Any> {
                TODO("Not yet implemented")
            }

            override suspend fun getDogByMlId(mlDogId: String): ApiResponseStatus<Dog> {
                TODO("Not yet implemented")
            }

        }

        val viewModel = DogListViewModel(DogRepo())

        composeTestRule.setContent {
            DogListView(
                onItemClick = { },
                onNavigationBackClick = { },
                viewModel = viewModel,
            )
        }

        composeTestRule.onNodeWithTag(testTag = "loading-wheel").assertIsDisplayed()
    }

    @Test
    fun errorDialogShowsIfErrorWhenGetDogs(){
        class DogRepo: DogRepositoryTask{
            override suspend fun getDogCollection(): ApiResponseStatus<List<Dog>> {
                return ApiResponseStatus.Error(R.string.error_undefine)
            }

            override suspend fun addDogToUser(dogId: Long): ApiResponseStatus<Any> {
                TODO("Not yet implemented")
            }

            override suspend fun getDogByMlId(mlDogId: String): ApiResponseStatus<Dog> {
                TODO("Not yet implemented")
            }

        }

        val viewModel = DogListViewModel(DogRepo())

        composeTestRule.setContent {
            DogListView(
                onItemClick = { },
                onNavigationBackClick = { },
                viewModel = viewModel,
            )
        }

        composeTestRule.onNodeWithTag(testTag = "error_dialog").assertIsDisplayed()
    }

    @Test
    fun dogListShowsIfSuccessGettingDogs(){
        val dog1 = Dog(id = 1, index = 1, name = "Chihuahua")
        val dog2 = Dog(id = 19, index = 199999, name = "Puppet", inCollection = false)
        class DogRepo: DogRepositoryTask{
            override suspend fun getDogCollection(): ApiResponseStatus<List<Dog>> {
                return ApiResponseStatus.Success(
                    listOf(
                        dog1,
                        dog2
                    )
                )
            }

            override suspend fun addDogToUser(dogId: Long): ApiResponseStatus<Any> {
                TODO("Not yet implemented")
            }

            override suspend fun getDogByMlId(mlDogId: String): ApiResponseStatus<Dog> {
                TODO("Not yet implemented")
            }

        }

        val viewModel = DogListViewModel(DogRepo())

        composeTestRule.setContent {
            DogListView(
                onItemClick = { },
                onNavigationBackClick = { },
                viewModel = viewModel,
            )
        }

        composeTestRule.onNodeWithTag(testTag = "dog_${dog1.name}", useUnmergedTree = true)
            .assertIsDisplayed()
        composeTestRule.onNodeWithText(text = dog2.index.toString())
            .assertIsDisplayed()
    }

}