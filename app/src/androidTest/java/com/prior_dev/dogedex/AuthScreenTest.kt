package com.prior_dev.dogedex

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.prior_dev.dogedex.api.ApiResponseStatus
import com.prior_dev.dogedex.auth.AuthRepositoryTask
import com.prior_dev.dogedex.auth.AuthScreen
import com.prior_dev.dogedex.auth.AuthViewModel
import com.prior_dev.dogedex.auth.UserRepositoryTask
import com.prior_dev.dogedex.models.User
import org.junit.Rule
import org.junit.Test

class AuthScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testClickRegisterButtonOpenSignUpScreen(){
         class Repository: AuthRepositoryTask{
             override suspend fun signUp(
                 email: String,
                 password: String,
                 passwordConfirmation: String,
             ): ApiResponseStatus<User> {
                 return ApiResponseStatus.Success(
                     User(
                         1,
                         "Usuario",
                         "dfajdlkfjlkjflkdalkfd"
                     )
                 )
             }

             override suspend fun login(email: String, password: String): ApiResponseStatus<User> {
                 TODO("Not yet implemented")
             }
         }

        class FakeUserRepo: UserRepositoryTask{
            override fun setLoggedInUser(user: User) {
                TODO("Not yet implemented")
            }

            override fun getLoggedInUser(): User? {
                TODO("Not yet implemented")
            }

            override fun logout() {
                TODO("Not yet implemented")
            }
        }

        val viewModel = AuthViewModel(Repository(), FakeUserRepo())
        
        composeTestRule.setContent { 
            AuthScreen(
                onUserLogIn = {},
                authViewModel = viewModel
            )
        }

        composeTestRule.onNodeWithTag(testTag = "login_button", useUnmergedTree = true).assertIsDisplayed()
        composeTestRule.onNodeWithTag(testTag = "register_text_button",  useUnmergedTree = true).performClick()
        composeTestRule.onNodeWithTag(testTag = "sign_up_button",  useUnmergedTree = true).assertIsDisplayed()
    }

    @Test
    fun testEmailErrorShowsIfClickLoginButtonAndHasNotEmail(){
         class FakeAuthRepo: AuthRepositoryTask{
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

        class FakeUserRepo: UserRepositoryTask{
            override fun setLoggedInUser(user: User) {
                TODO("Not yet implemented")
            }

            override fun getLoggedInUser(): User? {
                TODO("Not yet implemented")
            }

            override fun logout() {
                TODO("Not yet implemented")
            }
        }

        val viewModel = AuthViewModel(FakeAuthRepo(), FakeUserRepo())

        composeTestRule.setContent {
            AuthScreen(
                onUserLogIn = {},
                authViewModel = viewModel
            )
        }

        composeTestRule.onNodeWithTag(testTag = "login_button", useUnmergedTree = true)
            .performClick()
        composeTestRule.onNodeWithTag(testTag = "email_field_error",  useUnmergedTree = true)
            .assertIsDisplayed()

        composeTestRule.onNodeWithTag(testTag = "password_field",  useUnmergedTree = true)
            .performTextInput("hola")

        composeTestRule.onNodeWithTag(testTag = "login_button", useUnmergedTree = true)
            .performClick()

        Espresso.onView(withId(R.id.take_photo_fab)).check(matches(isDisplayed()))
    }
}