package com.prior_dev.dogedex.auth

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LiveData
import com.prior_dev.dogedex.R
import com.prior_dev.dogedex.api.ApiResponseStatus
import com.prior_dev.dogedex.composables.AuthField
import com.prior_dev.dogedex.composables.ErrorDialog
import com.prior_dev.dogedex.composables.LoadingWheel
import com.prior_dev.dogedex.models.User

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginView(
//    status: LiveData<ApiResponseStatus<User>>? = null,
//    onErrorDismiss: () -> Unit,
    onLoginButtonClick: (String, String) -> Unit,
    onRegisterButtonClick: () -> Unit,
    authViewModel: AuthViewModel
) {
    Scaffold(
        topBar = { LoginToolBar() }
    ) { innerPadding ->
       Content(
           modifier = Modifier.padding(innerPadding),
           onLoginButtonClick = onLoginButtonClick,
           onRegisterButtonClick = onRegisterButtonClick,
           authViewModel = authViewModel
       )
    }

//    if(status is ApiResponseStatus.Loading<*>){
//        LoadingWheel()
//    }else if(status is ApiResponseStatus.Error<*>){
//        ErrorDialog(messageId = status.messageId, onErrorDismiss = onErrorDismiss)
//    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginToolBar() {
    TopAppBar(
        title = { Text(text = stringResource(id = R.string.app_name)) },
    )
}

@Composable
fun Content(
    modifier: Modifier = Modifier,
    onLoginButtonClick: (String, String) -> Unit,
    onRegisterButtonClick: () -> Unit,
    authViewModel: AuthViewModel
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
            .padding(top = 32.dp)
    ){
        AuthField(
            modifier = Modifier.fillMaxWidth(),
            label = stringResource(id = R.string.email),
            text = email,
            errorMessageId = authViewModel.emailError.value
        ) {
            email = it
            authViewModel.resetErrors()
        }

        Spacer(modifier = Modifier.height(16.dp))

        AuthField(
            modifier = Modifier.fillMaxWidth(),
            label = stringResource(id = R.string.password),
            text = password,
            visualTransformation = PasswordVisualTransformation(),
            errorMessageId = authViewModel.passwordError.value
        ) {
            password = it
            authViewModel.resetErrors()
        }

        Button(modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp),
            onClick = {
                onLoginButtonClick(email, password)
            }
        ) {
            Text(
                stringResource(R.string.login),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Medium
            )
        }

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            textAlign = TextAlign.Center,
            text = stringResource(R.string.do_not_have_an_account)
        )

        Text(
            modifier = Modifier
                .clickable(enabled = true, onClick = {
                    onRegisterButtonClick()
                })
                .fillMaxWidth()
                .padding(16.dp),
            text = stringResource(R.string.register),
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Medium
        )
    }
}

