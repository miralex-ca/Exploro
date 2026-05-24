package com.muralex.myapp

actual class Platform actual constructor() {
    actual val system: String = IOS_PLATFORM_SYSTEM_NAME
}