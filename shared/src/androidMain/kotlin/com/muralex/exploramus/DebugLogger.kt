package com.muralex.exploramus

actual class DebugLogger actual constructor(tagString : String) {
    actual val tag = tagString
    actual fun log(message: String) {
        println("$tag: $message")
    }
}