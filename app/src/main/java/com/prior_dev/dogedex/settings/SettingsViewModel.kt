package com.prior_dev.dogedex.settings

import androidx.lifecycle.ViewModel
import com.prior_dev.dogedex.auth.UserRepositoryTask
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val userRepo: UserRepositoryTask
): ViewModel() {
    fun logout(){
        userRepo.logout()
    }
}