package com.muralex.myapp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform