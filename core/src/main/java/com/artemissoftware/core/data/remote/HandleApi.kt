package com.artemissoftware.core.data.remote

import com.artemissoftware.core.data.remote.dto.ErrorDto
import com.artemissoftware.core.data.remote.exceptions.TaskyNetworkError
import com.artemissoftware.core.data.remote.exceptions.TaskyNetworkException
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import retrofit2.HttpException
import java.net.UnknownHostException
import java.util.concurrent.CancellationException

object HandleApi {

    suspend fun <T> safeApiCall(callFunction: suspend () -> T): T {
        return try {

            callFunction.invoke()

        } catch (ex: Exception) {

            when (ex) {
                is CancellationException ->{
                    throw TaskyNetworkException(TaskyNetworkError.Cancellation)
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
                else ->{
                    throw TaskyNetworkException( )
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