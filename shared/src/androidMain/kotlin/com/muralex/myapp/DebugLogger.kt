package com.muralex.myapp

actual class DebugLogger actual constructor(tagString : String) {
    actual val tag = tagString
    actual fun log(message: String) {
        println("$tag: $message")
    }
}