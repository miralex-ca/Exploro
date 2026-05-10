package com.muralex.models

sealed interface DataResult<out T> {

    data class Success<T>(
        val data: T
    ) : DataResult<T>

    data class Error(
        val error: AppError
    ) : DataResult<Nothing>
}

sealed interface AppError {
    data object Network : AppError
    data object Unexpected : AppError
}

