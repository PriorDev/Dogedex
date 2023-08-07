package com.prior_dev.dogedex.api.response

import com.squareup.moshi.Json


data class DefaultResponse (
    val message: String,
    @field:Json(name = "is_success") val isSuccess: Boolean,
)