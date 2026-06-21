package com.exploramus.data.common

import com.exploramus.core.models.AppInfo

interface PlatformInfoProvider {
    fun getAppInfo(): AppInfo
}