package com.prior_dev.dogedex

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performTextInput
import com.prior_dev.dogedex.api.ApiResponseStatus
import com.prior_dev.dogedex.auth.AuthRepositoryTask
import com.prior_dev.dogedex.auth.LoginActivity
import com.prior_dev.dogedex.di.AuthModule
import com.prior_dev.dogedex.di.DogSelfModule
import com.prior_dev.dogedex.models.User
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import dagger.hilt.components.SingletonComponent
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import javax.inject.Singleton

class FakeAuthRepository @Inject constructor(): AuthRepositoryTask{
    override suspend fun signUp(
        email: String,
        password: String,
        passwordConfirmation: String,
    ): ApiResponseStatus<User> {
        TODO("Not yet implemented")
    }

    override suspend fun login(email: String, password: String): ApiResponseStatus<User> {
        TODO("Not yet implemented")
    }
}

@UninstallModules(AuthModule::class)
@HiltAndroidTest
class LoginActivityTest {
    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<LoginActivity>()

    @Module
    @InstallIn(ViewModelComponent::class)
    abstract class DogSelfTestModule {
        @ViewModelScoped
        @Binds
        abstract fun providesTestAuthRepository(
            fakeAuthRepository: FakeAuthRepository
        ): AuthRepositoryTask
    }

    @Test
    fun mainActivityOpenAfterUserLogin(){
        val context = composeTestRule.activity

        composeTestRule
            .onNodeWithText(context.getString(R.string.login))
            .assertIsDisplayed()

        composeTestRule.onNodeWithTag(testTag = "email_field",  useUnmergedTree = true)
            .performTextInput("hola")

        composeTestRule.onNodeWithTag(testTag = "email_field",  useUnmergedTree = true)
            .performTextInput("hola")
    }

}