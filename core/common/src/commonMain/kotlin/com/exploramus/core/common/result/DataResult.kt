package com.exploramus.core.common.result

sealed interface DataResult<out T> {
    data class Success<out T>(val data: T) : DataResult<T>
    data class Error(val error: DataError?) : DataResult<Nothing>
}

fun DataResult<*>.isSuccess() = this is DataResult.Success
fun DataResult<*>.isFailure() = this is DataResult.Error


