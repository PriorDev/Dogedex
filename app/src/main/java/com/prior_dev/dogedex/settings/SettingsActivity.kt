package com.prior_dev.dogedex.settings

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.hilt.navigation.compose.hiltViewModel
import com.prior_dev.dogedex.R
import com.prior_dev.dogedex.auth.AuthViewModel
import com.prior_dev.dogedex.auth.LoginActivity
import com.prior_dev.dogedex.databinding.ActivitySettingsBinding
import com.prior_dev.dogedex.models.User
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsActivity : AppCompatActivity() {
    private val viewModel: SettingsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.logoutButton.setOnClickListener {
            logout()
        }
    }

    private fun logout() {
        viewModel.logout()
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK

        startActivity(intent)
    }
}