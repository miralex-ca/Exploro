package com.exploramus.data.repository

import android.content.Context
import android.os.Build
import com.exploramus.core.models.AppInfo
import com.exploramus.data.common.PlatformInfoProvider

fun createAndroidPlatformInfoProvider(context: Context): PlatformInfoProvider = AndroidPlatformInfoProvider(context)

class AndroidPlatformInfoProvider(
    private val context: Context
) : PlatformInfoProvider {

    override fun getAppInfo(): AppInfo {

        val packageInfo = context.packageManager
            .getPackageInfo(context.packageName, 0)

        return AppInfo(
            appVersion = packageInfo.versionName ?: "Unknown",
            platformName = "Android",
            osVersion = Build.VERSION.RELEASE,
            deviceModel = Build.MODEL,
        )
    }
}