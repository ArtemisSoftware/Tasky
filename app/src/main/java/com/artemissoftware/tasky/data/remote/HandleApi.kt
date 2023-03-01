package com.artemissoftware.tasky.data.remote

import com.artemissoftware.tasky.data.remote.dto.ErrorDto
import com.artemissoftware.tasky.data.remote.exceptions.NetworkErrors
import com.artemissoftware.tasky.data.remote.exceptions.TaskyNetworkException
import com.google.gson.Gson
import retrofit2.HttpException
import java.net.UnknownHostException

object HandleApi {

    suspend fun <T> safeApiCall(callFunction: suspend () -> T): T {
        return try {

            callFunction.invoke()

        } catch (ex: Exception) {

            when (ex) {
                is HttpException -> {

                    convertErrorBody(ex)?.let { error ->
                        throw TaskyNetworkException(
                            code = ex.code(),
                            message = error.message
                        )
                    } ?: run {
                        throw TaskyNetworkException()
                    }

                }
                is UnknownHostException ->{
                    throw TaskyNetworkException(code = NetworkErrors.UNKNOWN_HOST.first, message = ex.message)
                }
                else -> throw TaskyNetworkException(code = NetworkErrors.GENERIC_API_ERROR.first, message = NetworkErrors.GENERIC_API_ERROR.second)
            }
        }
    }

    private fun convertErrorBody(httpException: HttpException): ErrorDto? {
        return try {
            httpException.response()?.errorBody()?.let {
                Gson().fromJson(it.charStream(), ErrorDto::class.java)
            }
        } catch (exception: Exception) {
            null
        }
    }
}