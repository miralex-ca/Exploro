package com.muralex.network

import com.muralex.core.common.logging.Log
import com.muralex.core.common.result.DataError
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.HttpRequestTimeoutException
import io.ktor.client.plugins.ServerResponseException
import kotlinx.io.IOException

sealed interface NetworkResult<out T> {
    data class Success<T>(
        val data: T
    ) : NetworkResult<T>

    data class Error(
        val error: NetworkError
    ) : NetworkResult<Nothing>
}

sealed interface NetworkError {
    data object NoInternet : NetworkError
    data object ServerUnreachable : NetworkError
    data class HttpError(val code: Int) : NetworkError
    data object Unknown : NetworkError
}

fun Throwable.toNetworkError(): NetworkError {
    return when (this) {
        is IOException -> NetworkError.NoInternet
        is HttpRequestTimeoutException -> NetworkError.ServerUnreachable
        is ClientRequestException -> NetworkError.HttpError(response.status.value)
        is ServerResponseException -> NetworkError.HttpError(response.status.value)
        else -> NetworkError.Unknown
    }
}

fun NetworkError.toDataError(): DataError.Network {
    return when (this) {
        NetworkError.NoInternet -> DataError.Network.NO_INTERNET
        NetworkError.ServerUnreachable -> DataError.Network.SERVER_ERROR
        is NetworkError.HttpError -> when (code) {
            408 -> DataError.Network.REQUEST_TIMEOUT
            429 -> DataError.Network.TOO_MANY_REQUESTS
            in 500..599 -> DataError.Network.SERVER_ERROR
            else -> DataError.Network.UNKNOWN
        }
        NetworkError.Unknown ->
            DataError.Network.UNKNOWN
    }.also {
        Log.e("Network error: $it")
    }
}