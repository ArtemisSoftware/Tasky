package com.artemissoftware.core.data.remote

import com.artemissoftware.core.data.remote.dto.ErrorDto
import com.artemissoftware.core.data.remote.exceptions.TaskyNetworkError
import com.artemissoftware.core.data.remote.exceptions.TaskyNetworkException
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import kotlinx.coroutines.delay
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.util.concurrent.CancellationException

object HandleApi {

    suspend fun <T> safeApiCall(callFunction: suspend () -> T): T {
        return try {

            callFunction.invoke()

        } catch (ex: Exception) {

            when (ex) {
                is CancellationException ->{
                    throw ex
                }
                is HttpException -> {
                    convertErrorBody(ex)?.let { error ->
                        throw TaskyNetworkException(
                            code = ex.code(),
                            description = error.message
                        )
                    } ?: run {
                        throw TaskyNetworkException(code = ex.code(), description = ex.message())
                    }

                }
                is UnknownHostException ->{
                    throw TaskyNetworkException(TaskyNetworkError.UnknownHost)
                }
                is ConnectException ->{
                    throw TaskyNetworkException(TaskyNetworkError.GenericApiError)
                }
                is SocketTimeoutException ->{
                    throw TaskyNetworkException(TaskyNetworkError.GenericApiError)
                }
                else ->{
                    throw TaskyNetworkException()
                }
            }
        }
    }

    private fun convertErrorBody(httpException: HttpException): ErrorDto? {
        return try {
            httpException.response()?.errorBody()?.let {

                val moshi: Moshi = Moshi.Builder().build()
                val adapter: JsonAdapter<ErrorDto> = moshi.adapter(ErrorDto::class.java)
                adapter.fromJson(it.string())
            }
        } catch (exception: Exception) {
            null
        }
    }
}