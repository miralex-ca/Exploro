package com.muralex.data.repository

import com.muralex.data.common.PlatformInfoProvider
import com.muralex.models.AppInfo
import platform.Foundation.NSBundle
import platform.UIKit.UIDevice


fun createAndroidPlatformInfoProvider(): PlatformInfoProvider = IOSPlatformInfoProvider()

class IOSPlatformInfoProvider : PlatformInfoProvider {
    override fun getAppInfo(): AppInfo {
        val infoDictionary = NSBundle.mainBundle.infoDictionary

        val appVersion = infoDictionary
            ?.get("CFBundleShortVersionString") as? String
            ?: "Unknown"

        val osVersion = UIDevice.currentDevice.systemVersion
        val deviceModel = UIDevice.currentDevice.model

        return AppInfo(
            appVersion = appVersion,
            platformName = "iOS",
            osVersion = osVersion,
            deviceModel = deviceModel
        )
    }
}