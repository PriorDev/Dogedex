package com.prior_dev.dogedex.auth

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.prior_dev.dogedex.dogdetail.ui.theme.DogedexTheme
import com.prior_dev.dogedex.main.MainActivity
import com.prior_dev.dogedex.models.User

class LoginActivity : ComponentActivity()
{
    private val viewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val user = viewModel.user
        val status = viewModel.status

        user.value?.let{
            User.setLoggedInUser(this, it)
            startMainActivity()
        }

        setContent {
            DogedexTheme {
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