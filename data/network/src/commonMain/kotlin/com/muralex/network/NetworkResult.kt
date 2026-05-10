package com.muralex.network

import com.muralex.models.AppError
import kotlinx.io.IOException

sealed interface NetworkResult<out T> {
    data class Success<T>(
        val data: T
    ) : NetworkResult<T>

    data class Error(
        val message: String,
        val throwable: Throwable? = null
    ) : NetworkResult<Nothing>
}

fun NetworkResult.Error.toAppError(): AppError {
    println("NetworkResult error: $message")
    return when (throwable) {
        is IOException -> AppError.Network
        else -> AppError.Unexpected
    }
}