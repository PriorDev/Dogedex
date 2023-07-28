package com.prior_dev.dogedex.api.dto

import com.squareup.moshi.Json

data class LoginDto(
    val email: String,
    val password: String,
)
