package com.prior_dev.dogedex

import android.Manifest
import androidx.camera.core.ImageProxy
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.GrantPermissionRule
import com.prior_dev.dogedex.api.ApiResponseStatus
import com.prior_dev.dogedex.auth.UserRepository
import com.prior_dev.dogedex.auth.UserRepositoryTask
import com.prior_dev.dogedex.di.DogSelfModule
import com.prior_dev.dogedex.di.UserModule
import com.prior_dev.dogedex.doglist.DogRepositoryTask
import com.prior_dev.dogedex.machinglearning.ClassifierRepositoryTask
import com.prior_dev.dogedex.machinglearning.DogRecognition
import com.prior_dev.dogedex.main.MainActivity
import com.prior_dev.dogedex.models.Dog
import com.prior_dev.dogedex.models.User
import com.prior_dev.dogedex.testutils.EspressoIdlingResource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import dagger.hilt.components.SingletonComponent
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import javax.inject.Singleton


@UninstallModules(DogSelfModule::class, UserModule::class)
@HiltAndroidTest
class MainActivityTest {
    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val runtimePermissionRule: GrantPermissionRule = GrantPermissionRule.grant(Manifest.permission.CAMERA)

    @get:Rule(order = 2)
    val composeTestRule = createComposeRule()

    @get:Rule(order = 3)
    val activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    private val dog1 = Dog(id = 1, index = 1, name = "Chihuahua")

    @InstallIn(ViewModelComponent::class)
    @Module
    abstract class FakeDogTaskModule {
        @ViewModelScoped
        @Binds
        abstract fun provideDogRepository(dogRepository: FakeDogRepository): DogRepositoryTask

        @ViewModelScoped
        @Binds
        abstract fun providesClassifier(classifierRepository: FakeClassifierRepository): ClassifierRepositoryTask
    }

    @Module
    @InstallIn(SingletonComponent::class)
    abstract class UserModule {
        @Singleton
        @Binds
        abstract fun providesUserRepo(userRepository: FakeUserRepo): UserRepositoryTask
    }

    class FakeDogRepository @Inject constructor(): DogRepositoryTask{
        private val dog1 = Dog(id = 1, index = 1, name = "Chihuahua")
        val dog2 = Dog(id = 19, index = 199999, name = "Puppet", inCollection = false)

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
            return ApiResponseStatus.Success(dog1)
        }
    }

    class FakeClassifierRepository @Inject constructor(): ClassifierRepositoryTask{
        override suspend fun recognizeImage(imageProxy: ImageProxy): DogRecognition {
            return  DogRecognition("dahfkjad", 90f)
        }
    }

    class FakeUserRepo @Inject constructor(): UserRepositoryTask{
        override fun setLoggedInUser(user: User) {

        }

        override fun getLoggedInUser(): User? {
            return User(1, "nombre@test.com", "dsafkdjaflkd")
        }

        override fun logout() {
            TODO("Not yet implemented")
        }

    }

    @Before
    fun registerIdlingResource(){
        IdlingRegistry.getInstance().register(EspressoIdlingResource.idlingResource)
    }

    @Test
    fun showAllFab(){
        onView(withId(R.id.take_photo_fab)).check(matches(isDisplayed()))
        onView(withId(R.id.dog_list_fab)).check(matches(isDisplayed()))
        onView(withId(R.id.settings)).check(matches(isDisplayed()))
    }

    @Test
    fun dogListOpenWhenClickButton(){
        onView(withId(R.id.dog_list_fab)).perform(click())

        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val string = context.getString(R.string.my_dog_collection)
        composeTestRule.onNodeWithText(text = string).assertIsDisplayed()
    }

    @Test
    fun whenRecognizingDogDetailsScreenOpens(){
        onView(withId(R.id.take_photo_fab)).perform(click())

        composeTestRule.onNodeWithTag(testTag = "close_details_screen_fab").assertIsDisplayed()
    }

    @After
    fun unregisterResource(){
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.idlingResource)
    }
}