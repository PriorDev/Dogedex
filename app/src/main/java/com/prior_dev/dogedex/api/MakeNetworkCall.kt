package com.prior_dev.dogedex.api

import com.prior_dev.dogedex.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.UnknownHostException

suspend fun <T>makeNetworkCall(
    call: suspend () -> T
): ApiResponseStatus<T> {
    return withContext(Dispatchers.IO){
        try{
            ApiResponseStatus.Success(call())
        }catch (e: UnknownHostException){
            ApiResponseStatus.Error(R.string.unkown_host)
        }catch (e: Exception){
            ApiResponseStatus.Error(R.string.error_undefine)
        }
    }
}