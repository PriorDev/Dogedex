package com.prior_dev.dogedex.api.dto

import com.squareup.moshi.Json

data class UserDto(
    val id: Long,
    val email: String,
    @field:Json(name = "authentication_token")val authenticationToken: String
)
