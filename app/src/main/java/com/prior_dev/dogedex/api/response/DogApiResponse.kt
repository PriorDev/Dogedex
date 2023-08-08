package com.prior_dev.dogedex.api.response

import com.prior_dev.dogedex.api.dto.DogDto
import com.squareup.moshi.Json

data class DogApiResponse(
    val message: String,
    @field:Json(name = "is_success") val isSuccess: Boolean,
    val data: DogResponse
)
