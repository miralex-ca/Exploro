package com.muralex.exploramus

expect class DebugLogger (tagString : String) {
    val tag : String
    fun log(message: String)
}