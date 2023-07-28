package com.prior_dev.dogedex.api

import android.util.Log
import com.prior_dev.dogedex.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.net.UnknownHostException

private const val UNAUTHORIZED_HTTP_CODE = 401

suspend fun <T>makeNetworkCall(
    call: suspend () -> T
): ApiResponseStatus<T> {
    return withContext(Dispatchers.IO){
        try{
            ApiResponseStatus.Success(call())
        }catch (e: UnknownHostException){
            ApiResponseStatus.Error(R.string.unkown_host)
        }catch (e: HttpException){
            val errorId = when(e.code()){
                UNAUTHORIZED_HTTP_CODE -> R.string.invalid_pass_or_user
                else -> R.string.error_undefine
            }

            Log.e("TAG", "makeNetworkCall: ${e.message}" )
            ApiResponseStatus.Error(errorId)
        }catch (e: Exception){
            val errorId = when(e.message){
                "sign_up_error" -> R.string.error_sign_up
                "sign_in_error" -> R.string.error_login_in
                "user_already_exists" -> R.string.user_already_exists
                else -> {
                    Log.e("TAG", "makeNetworkCall: ${e.message}" )
                    R.string.error_undefine
                }
            }
            ApiResponseStatus.Error(errorId)
        }
    }
}