package com.muralex.myapp

expect class DebugLogger (tagString : String) {
    val tag : String
    fun log(message: String)
}