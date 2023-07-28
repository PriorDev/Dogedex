package com.prior_dev.dogedex.auth

import android.content.Context
import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.prior_dev.dogedex.R
import com.prior_dev.dogedex.databinding.FragmentSignUpBinding
import com.prior_dev.dogedex.isValidEmail

class SignUpFragment : Fragment() {
    interface SignUpFragmentActions{
        fun onSignUpFieldsValidated(email: String, password: String, passwordConfirmation: String)
    }

    private lateinit var signUpFragmentActions: SignUpFragmentActions

    override fun onAttach(context: Context){
        super.onAttach(context)
        signUpFragmentActions = try {
            context as SignUpFragmentActions
        }catch (e: ClassCastException){
            throw ClassCastException("$context must implement signUpFragmentActions")
        }
    }

    private lateinit var binding: FragmentSignUpBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentSignUpBinding.inflate(layoutInflater)
        setUpSignUpButton()
        return binding.root
    }

    private fun setUpSignUpButton() {
        binding.signUpButton.setOnClickListener {
            validateFields()
        }
    }

    private fun validateFields() {
        binding.emailEdit.error = null
        binding.passwordEdit.error = null
        binding.confirmPasswordEdit.error = null

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

        val passwordConfirmation = binding.confirmPasswordEdit.text.toString()
        if(passwordConfirmation.isBlank()){
            binding.confirmPasswordEdit.error = getString(R.string.password_must_no_be_empty)
            return
        }

        if(password != passwordConfirmation){
            binding.confirmPasswordEdit.error = getString(R.string.passwords_does_not_match)
            return
        }

        signUpFragmentActions.onSignUpFieldsValidated(email, password, passwordConfirmation)
    }
}