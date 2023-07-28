package com.prior_dev.dogedex

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.prior_dev.dogedex.auth.LoginActivity
import com.prior_dev.dogedex.databinding.ActivityMainBinding
import com.prior_dev.dogedex.models.User
import com.prior_dev.dogedex.settings.SettingsActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val user = User.getLoggedInUser(this)
        if(user == null){
            openLoginActivity()
            return
        }

        binding.settings.setOnClickListener{
            openSettingsActivity()
        }
    }

    private fun openSettingsActivity() {
        startActivity(Intent(this, SettingsActivity::class.java))
    }

    private fun openLoginActivity() {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }
}