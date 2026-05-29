package com.muralex.core.common.logging

object Log {
    private const val TAG = "D-KMP"

    fun d(message: String) {
        println("[$TAG] $message")
    }

    fun e(message: String, throwable: Throwable? = null) {
        println("❌[$TAG] $message")
        throwable?.printStackTrace()
    }
}

