package com.prior_dev.dogedex.models

import android.app.Activity
import android.content.Context
import com.prior_dev.dogedex.api.dto.UserDto

data class User(
    val id: Long,
    val email: String,
    val authenticationToken: String
)

fun UserDto.toDomain() =
    User(
        id = id,
        email = email,
        authenticationToken = authenticationToken
    )