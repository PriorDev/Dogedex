package com.prior_dev.dogedex.models

import android.app.Activity
import android.content.Context
import com.prior_dev.dogedex.api.dto.UserDto

data class User(
    val id: Long,
    val email: String,
    val authenticationToken: String
){
    companion object{
        private const val AUTH_PREF = "aut_pref"
        private const val ID_KEY = "id"
        private const val EMAIL_KEY = "email"
        private const val AUTH_TOKEN_KEY = "token"

        fun setLoggedInUser(activity: Activity, user: User){
            activity.getSharedPreferences(AUTH_PREF, Context.MODE_PRIVATE).also {
                it.edit()
                    .putLong(ID_KEY, user.id)
                    .putString(EMAIL_KEY, user.email)
                    .putString(AUTH_TOKEN_KEY, user.authenticationToken)
                    .apply()
            }
        }

        fun getLoggedInUser(activity: Activity): User? {
            val prefs =
                activity.getSharedPreferences(AUTH_PREF, Context.MODE_PRIVATE) ?: return null

            val id = prefs.getLong(ID_KEY, 0)
            if (id == 0L)
                return null

            return User(
                id = id,
                email = prefs.getString(EMAIL_KEY, "") ?: "",
                authenticationToken = prefs.getString(AUTH_TOKEN_KEY, "") ?: ""
            )
        }

        fun logout(activity: Activity){
            activity.getSharedPreferences(AUTH_PREF, Context.MODE_PRIVATE).also {
                it.edit()
                    .clear()
                    .apply()
            }
        }
    }
}

fun UserDto.toDomain() =
    User(
        id = id,
        email = email,
        authenticationToken = authenticationToken
    )