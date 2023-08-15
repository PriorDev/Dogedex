package com.prior_dev.dogedex.auth

import android.content.Context
import com.prior_dev.dogedex.models.User
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

interface UserRepositoryTask{
    fun setLoggedInUser(user: User)
    fun getLoggedInUser(): User?
    fun logout()
}

class UserRepository @Inject constructor(
    @ApplicationContext private val context: Context
): UserRepositoryTask {
    companion object{
        private const val AUTH_PREF = "aut_pref"
        private const val ID_KEY = "id"
        private const val EMAIL_KEY = "email"
        private const val AUTH_TOKEN_KEY = "token"
    }

    override fun setLoggedInUser(user: User){
        context.getSharedPreferences(AUTH_PREF, Context.MODE_PRIVATE).also {
            it.edit()
                .putLong(ID_KEY, user.id)
                .putString(EMAIL_KEY, user.email)
                .putString(AUTH_TOKEN_KEY, user.authenticationToken)
                .apply()
        }
    }

    override fun getLoggedInUser(): User? {
        val prefs =
            context.getSharedPreferences(AUTH_PREF, Context.MODE_PRIVATE) ?: return null

        val id = prefs.getLong(ID_KEY, 0)
        if (id == 0L)
            return null

        return User(
            id = id,
            email = prefs.getString(EMAIL_KEY, "") ?: "",
            authenticationToken = prefs.getString(AUTH_TOKEN_KEY, "") ?: ""
        )
    }

    override fun logout(){
        context.getSharedPreferences(AUTH_PREF, Context.MODE_PRIVATE).also {
            it.edit().clear().apply()
        }
    }
}