package com.prior_dev.dogedex.api.response

import com.prior_dev.dogedex.api.dto.DogDto

data class DogListResponse(
    val dogs: List<DogDto>
)
