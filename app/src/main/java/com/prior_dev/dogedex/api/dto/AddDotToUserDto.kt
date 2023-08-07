package com.prior_dev.dogedex.api.dto

import com.squareup.moshi.Json

data class AddDotToUserDto(
    @field:Json(name = "dog_id") val dogId: Long
)
