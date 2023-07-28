package com.prior_dev.dogedex.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.navigation.findNavController
import com.prior_dev.dogedex.MainActivity
import com.prior_dev.dogedex.R
import com.prior_dev.dogedex.api.ApiResponseStatus
import com.prior_dev.dogedex.databinding.ActivityLoginBinding
import com.prior_dev.dogedex.models.User

class LoginActivity : AppCompatActivity(),
    LoginFragment.LoginFragmentActions,
    SignUpFragment.SignUpFragmentActions
{
    private val viewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val loadingWheel = binding.loadingWheel

        viewModel.status.observe(this){ status ->
            when(status){
                is ApiResponseStatus.Loading -> loadingWheel.visibility = View.VISIBLE
                is ApiResponseStatus.Success -> loadingWheel.visibility = View.GONE
                is ApiResponseStatus.Error -> {
                    loadingWheel.visibility = View.GONE
                    showErrorDialog(status.messageId)
                }
            }
        }

        viewModel.user.observe(this){ user ->
            if(user != null){
                User.setLoggedInUser(this, user)
                startMainActivity()
                finish()
            }
        }
    }

    private fun startMainActivity() {
        startActivity(Intent(this, MainActivity::class.java))
    }

    override fun onRegisterButtonClick() {
        findNavController(R.id.nav_host_fragment)
            .navigate(LoginFragmentDirections.actionLoginFragmentToSignUpFragment())
    }

    override fun onLoginFieldsValidate(email: String, password: String) {
        viewModel.login(email, password)
    }

    override fun onSignUpFieldsValidated(
        email: String,
        password: String,
        passwordConfirmation: String,
    ) {
        viewModel.signUp(email, password, passwordConfirmation)
    }

    private fun showErrorDialog(messageId: Int){
        AlertDialog.Builder(this)
            .setTitle(R.string.error)
            .setMessage(messageId)
            .setPositiveButton(android.R.string.ok){_,_ ->}
            .create()
            .show()
    }
}