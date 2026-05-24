package com.muralex.myapp

import com.russhwolf.settings.Settings


expect class Platform() {
    val system: String
}

const val ANDROID_PLATFORM_SYSTEM_NAME = "ANDROID"
const val IOS_PLATFORM_SYSTEM_NAME = "IOS"