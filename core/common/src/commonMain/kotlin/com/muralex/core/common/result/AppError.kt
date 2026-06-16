package com.muralex.core.common.result

sealed interface AppError

sealed interface DataError: AppError {
    enum class Network: DataError {
        REQUEST_TIMEOUT,
        TOO_MANY_REQUESTS,
        NO_INTERNET,
        SERVER_ERROR,
        UNKNOWN
    }

    data object Database: DataError
    data object ReadAsset: DataError
}