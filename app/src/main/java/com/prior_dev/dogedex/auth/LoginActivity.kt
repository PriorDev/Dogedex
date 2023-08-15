package com.prior_dev.dogedex.auth

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.prior_dev.dogedex.dogdetail.ui.theme.DogedexTheme
import com.prior_dev.dogedex.main.MainActivity
import com.prior_dev.dogedex.main.MainViewModel
import com.prior_dev.dogedex.models.User
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : ComponentActivity()
{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            DogedexTheme {
                AuthScreen(
                    onUserLogIn = ::startMainActivity
                )
            }
        }
    }

    private fun startMainActivity() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}