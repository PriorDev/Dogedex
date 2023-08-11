package com.prior_dev.dogedex.auth

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.prior_dev.dogedex.dogdetail.ui.theme.DogedexTheme
import com.prior_dev.dogedex.main.MainActivity
import com.prior_dev.dogedex.models.User
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : ComponentActivity()
{
    private val viewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val status = viewModel.status

        setContent {
            DogedexTheme {
                val user = viewModel.user
                user.value?.let{
                    User.setLoggedInUser(this, it)
                    startMainActivity()
                }

                AuthScreen(
                    status = status.value,
                    onErrorDismiss = viewModel::resetApiResponseStatus,
                    onLoginButtonClick = viewModel::login,
                    onSignUpButtonClick = viewModel::signUp,
                    authViewModel = viewModel
                )
            }
        }
    }

    private fun startMainActivity() {
        startActivity(Intent(this, MainActivity::class.java))
    }
}