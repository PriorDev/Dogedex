package com.prior_dev.dogedex

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Dog(
    val id: Long,
    val index: Int,
    val type: String,
    val heightFemale: String,
    val heightMale: String,
    val imageUrl: String,
    val lifeExpectancy: String,
    val name: String,
    val weightFemale: String,
    val weightMale: String,
    val temperament: String,
) : Parcelable