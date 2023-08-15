package com.prior_dev.dogedex.auth

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.prior_dev.dogedex.R
import com.prior_dev.dogedex.composables.AuthField
import com.prior_dev.dogedex.composables.BackNavigationIcon

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreen(
    onSignUpButtonClick: (email: String, password: String, passwordConfirmation: String) -> Unit,
    onNavigationIconClick: () -> Unit,
    authViewModel: AuthViewModel,
) {
    Scaffold(
        topBar = {
            SignUpScreenToolbar {
                onNavigationIconClick()
                authViewModel.resetErrors()
            }
        }
    ) { innerPadding ->
        Content(
            modifier = Modifier.padding(innerPadding),
            resetFieldErrors = { authViewModel.resetErrors() },
            onSignUpButtonClick = onSignUpButtonClick,
            authViewModel = authViewModel,
        )
    }
}

@Composable
private fun Content(
    modifier: Modifier = Modifier,
    resetFieldErrors: () -> Unit,
    onSignUpButtonClick: (email: String, password: String, passwordConfirmation: String) -> Unit,
    authViewModel: AuthViewModel,
) {
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val confirmPassword = remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AuthField(
            label = stringResource(id = R.string.email),
            modifier = Modifier
                .fillMaxWidth(),
            text = email.value,
            onValueChanged = {
                email.value = it
                resetFieldErrors()
            },
            errorMessageId = authViewModel.emailError.value
        )

        AuthField(
            label = stringResource(id = R.string.password),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            text = password.value,
            onValueChanged = {
                password.value = it
                resetFieldErrors()
            },
            visualTransformation = PasswordVisualTransformation(),
            errorMessageId = authViewModel.passwordError.value,
        )

        AuthField(
            label = stringResource(id = R.string.confirm_password),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            text = confirmPassword.value,
            onValueChanged = {
                confirmPassword.value = it
//                resetFieldErrors()
            },
            visualTransformation = PasswordVisualTransformation(),
            errorMessageId = authViewModel.passwordConfirmError.value,
        )

        Button(
            modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
            .semantics { testTag = "sign_up_button" },
            onClick = {
                onSignUpButtonClick(email.value, password.value, confirmPassword.value)
            }
        ) {
            Text(
                stringResource(R.string.sign_up),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SignUpScreenToolbar(
    onNavigationIconClick: () -> Unit
) {
    TopAppBar(
        title = { Text(stringResource(R.string.app_name)) },
        navigationIcon = { BackNavigationIcon { onNavigationIconClick() } }
    )
}