package com.muralex.exploramus


expect class Platform() {
    val system: String
}

const val ANDROID_PLATFORM_NAME = "ANDROID"
const val IOS_PLATFORM_NAME = "IOS"