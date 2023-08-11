package com.prior_dev.dogedex.auth

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.prior_dev.dogedex.api.ApiResponseStatus
import com.prior_dev.dogedex.auth.AuthNavDestination.LoginScreenDestination
import com.prior_dev.dogedex.auth.AuthNavDestination.SignUpScreenDestination
import com.prior_dev.dogedex.composables.ErrorDialog
import com.prior_dev.dogedex.composables.LoadingWheel
import com.prior_dev.dogedex.models.User

@Composable
fun AuthScreen(
    status: ApiResponseStatus<User>?,
    onErrorDismiss: () -> Unit,
    onLoginButtonClick: (String, String) -> Unit,
    onSignUpButtonClick: (email: String, password: String, passwordConfirmation: String) -> Unit,
    authViewModel: AuthViewModel,
) {
    val navController = rememberNavController()
    AuthNavHost(
        navController = navController,
        onLoginButtonClick = onLoginButtonClick,
        onSignUpButtonClick = onSignUpButtonClick,
        authViewModel = authViewModel,
        status = status,
        onErrorDismiss = onErrorDismiss
    )

    if(status is ApiResponseStatus.Loading<User>){
        LoadingWheel()
    }else if(status is ApiResponseStatus.Error<User>){
        ErrorDialog(messageId = status.messageId, onErrorDismiss = onErrorDismiss)
    }
}

@Composable
private fun AuthNavHost(
    navController: NavHostController,
    onLoginButtonClick: (String, String) -> Unit,
    onSignUpButtonClick: (email: String, password: String, passwordConfirmation: String) -> Unit,
    authViewModel: AuthViewModel,
    status: ApiResponseStatus<User>?,
    onErrorDismiss: () -> Unit,
) {
    NavHost(
        navController = navController,
        startDestination = LoginScreenDestination
    ) {
        composable(route = LoginScreenDestination) {
            LoginView(
                onRegisterButtonClick = {
                    navController.navigate(SignUpScreenDestination)
                },
                onLoginButtonClick = onLoginButtonClick,
                authViewModel = authViewModel,
            )
        }

        composable(route = SignUpScreenDestination) {
            SignUpScreen(
                onNavigationIconClick = {
                    navController.navigateUp()
                },
                onSignUpButtonClick = onSignUpButtonClick,
                authViewModel = authViewModel
            )
        }


    }
}