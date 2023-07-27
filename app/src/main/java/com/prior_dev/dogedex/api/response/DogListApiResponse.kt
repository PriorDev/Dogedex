package com.prior_dev.dogedex.api.response

import com.squareup.moshi.Json

data class DogListApiResponse (
    val message: String,
    @field:Json(name = "is_success") val isSuccess: Boolean,
    val data: DogListResponse
)