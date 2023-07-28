package com.prior_dev.dogedex.auth

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.prior_dev.dogedex.R
import com.prior_dev.dogedex.databinding.FragmentLoginBinding
import com.prior_dev.dogedex.isValidEmail
import kotlin.ClassCastException

class LoginFragment : Fragment() {

    interface LoginFragmentActions{
        fun onRegisterButtonClick()
        fun onLoginFieldsValidate(email: String, password: String)
    }

    private lateinit var loginFragmentActions: LoginFragmentActions
    private lateinit var binding: FragmentLoginBinding

    override fun onAttach(context: Context){
        super.onAttach(context)
        loginFragmentActions = try {
            context as LoginFragmentActions
        }catch (e: ClassCastException){
            throw ClassCastException("$context must implement LoginFragmentActions")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentLoginBinding.inflate(layoutInflater)

        binding.loginRegisterButton.setOnClickListener {
            loginFragmentActions.onRegisterButtonClick()
        }

        binding.loginButton.setOnClickListener {
            validateFields()
        }

        return binding.root
    }

    private fun validateFields() {
        binding.emailEdit.error = null
        binding.passwordEdit.error = null

        val email = binding.emailEdit.text.toString()
        if(!isValidEmail(email)){
            binding.emailEdit.error = getString(R.string.email_no_empty)
            return
        }
        val password = binding.passwordEdit.text.toString()
        if(password.isBlank()){
            binding.passwordEdit.error = getString(R.string.password_must_no_be_empty)
            return
        }

        loginFragmentActions.onLoginFieldsValidate(email, password)
    }

}