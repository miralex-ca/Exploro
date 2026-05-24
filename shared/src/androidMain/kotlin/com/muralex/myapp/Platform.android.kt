package com.muralex.myapp

import android.os.Build
import java.util.Locale

actual class Platform actual constructor() {
    actual val system: String = ANDROID_PLATFORM_SYSTEM_NAME
}