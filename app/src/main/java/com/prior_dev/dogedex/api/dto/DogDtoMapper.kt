package com.prior_dev.dogedex.api.dto

import com.prior_dev.dogedex.Dog

fun DogDto.toDomain(): Dog{
    return Dog(
        id = id,
        type = type,
        heightFemale = heightFemale,
        heightMale = heightMale,
        index = index,
        imageUrl = imageUrl,
        lifeExpectancy = lifeExpectancy,
        name = name,
        weightFemale = weightFemale,
        weightMale = weightMale,
        temperament = temperament
    )
}
