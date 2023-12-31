package com.prior_dev.dogedex

import android.util.Patterns

fun isValidEmail(email: String?): Boolean {
    return !email.isNullOrBlank() && Patterns.EMAIL_ADDRESS.matcher(email).matches()
}