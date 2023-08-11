package com.prior_dev.dogedex.viewmodel

import com.prior_dev.dogedex.DogedexCoroutineRule
import com.prior_dev.dogedex.R
import com.prior_dev.dogedex.api.ApiResponseStatus
import com.prior_dev.dogedex.auth.AuthRepositoryTask
import com.prior_dev.dogedex.auth.AuthViewModel
import com.prior_dev.dogedex.models.User
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class AuthViewModelTest {
    @get:Rule
    val dogedexCoroutineRule = DogedexCoroutineRule()

    @Test
    fun testLoginValidationIsError(){
        class FakeAuthRepository: AuthRepositoryTask{
            override suspend fun signUp(
                email: String,
                password: String,
                passwordConfirmation: String,
            ): ApiResponseStatus<User> {
                return ApiResponseStatus.Success(User(1, "raul@test.com", "lhkj1"))
            }

            override suspend fun login(email: String, password: String): ApiResponseStatus<User> {
                return ApiResponseStatus.Success(User(0, "", ""))
            }
        }

        val viewModel = AuthViewModel(FakeAuthRepository())
        viewModel.login("", "lkdajfd")
        assert(viewModel.emailError.value == R.string.email_no_empty)

        viewModel.login("kjfalkd", "")
        assert(viewModel.passwordError.value == R.string.password_must_no_be_empty)
    }

    @Test
    fun testLoginValidationCorrect(){
        val fakeUser = User(1, "raul@test.com", "lhkj1")
        class FakeAuthRepository: AuthRepositoryTask{
            override suspend fun signUp(
                email: String,
                password: String,
                passwordConfirmation: String,
            ): ApiResponseStatus<User> {
                return ApiResponseStatus.Success(fakeUser)
            }

            override suspend fun login(email: String, password: String): ApiResponseStatus<User> {
                return ApiResponseStatus.Success(fakeUser)
            }
        }

        val viewModel = AuthViewModel(FakeAuthRepository())
        viewModel.login("dfadsfa", "lkdajfd")
        assertEquals(fakeUser.email, viewModel.user.value?.email)

    }

    @Test
    fun testSignUpValidations(){
        val fakeUser = User(1, "raul@test.com", "lhkj1")
        class FakeAuthRepository: AuthRepositoryTask{
            override suspend fun signUp(
                email: String,
                password: String,
                passwordConfirmation: String,
            ): ApiResponseStatus<User> {
                return ApiResponseStatus.Success(fakeUser)
            }

            override suspend fun login(email: String, password: String): ApiResponseStatus<User> {
                return ApiResponseStatus.Success(fakeUser)
            }
        }

        val viewModel = AuthViewModel(FakeAuthRepository())
        viewModel.signUp("", "", "")
        assertEquals(R.string.email_no_empty, viewModel.emailError.value)

        viewModel.signUp("hola", "", "")
        assertEquals(R.string.password_must_no_be_empty, viewModel.passwordError.value)

        viewModel.signUp("hola", "123", "")
        assertEquals(R.string.password_must_no_be_empty, viewModel.passwordConfirmError.value)

        viewModel.signUp("hola", "123", "12")
        assertEquals(R.string.passwords_does_not_match, viewModel.passwordConfirmError.value)
    }

    @Test
    fun testSignUpIsSuccess(){
        val fakeUser = User(1, "raul@test.com", "lhkj1")
        class FakeAuthRepository: AuthRepositoryTask{
            override suspend fun signUp(
                email: String,
                password: String,
                passwordConfirmation: String,
            ): ApiResponseStatus<User> {
                return ApiResponseStatus.Success(fakeUser)
            }

            override suspend fun login(email: String, password: String): ApiResponseStatus<User> {
                return ApiResponseStatus.Success(fakeUser)
            }
        }

        val viewModel = AuthViewModel(FakeAuthRepository())

        viewModel.signUp(fakeUser.email, "123", "123")
        assertEquals(fakeUser, viewModel.user.value)
        assert(viewModel.status.value is ApiResponseStatus.Success)
    }

    @Test
    fun testSignUpIsError(){
        val fakeUser = User(1, "raul@test.com", "lhkj1")
        class FakeAuthRepository: AuthRepositoryTask{
            override suspend fun signUp(
                email: String,
                password: String,
                passwordConfirmation: String,
            ): ApiResponseStatus<User> {
                return ApiResponseStatus.Error(12)
            }

            override suspend fun login(email: String, password: String): ApiResponseStatus<User> {
                return ApiResponseStatus.Success(fakeUser)
            }
        }

        val viewModel = AuthViewModel(FakeAuthRepository())

        viewModel.signUp(fakeUser.email, "123", "123")
        assert(viewModel.status.value is ApiResponseStatus.Error)
    }

    @Test
    fun testResetApiResponseStatus(){
        val fakeUser = User(1, "raul@test.com", "lhkj1")
        class FakeAuthRepository: AuthRepositoryTask{
            override suspend fun signUp(
                email: String,
                password: String,
                passwordConfirmation: String,
            ): ApiResponseStatus<User> {
                return ApiResponseStatus.Error(12)
            }

            override suspend fun login(email: String, password: String): ApiResponseStatus<User> {
                return ApiResponseStatus.Success(fakeUser)
            }
        }

        val viewModel = AuthViewModel(FakeAuthRepository())

        viewModel.passwordError.value = 12
        viewModel.emailError.value = 12
        viewModel.passwordConfirmError.value = 12

        viewModel.resetErrors()

        assertEquals(null, viewModel.passwordError.value)
        assertEquals(null, viewModel.emailError.value)
        assertEquals(null, viewModel.passwordConfirmError.value)
    }
}