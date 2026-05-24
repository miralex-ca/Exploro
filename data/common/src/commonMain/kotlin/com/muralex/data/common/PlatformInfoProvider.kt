package com.muralex.data.common

import com.muralex.models.AppInfo

interface PlatformInfoProvider {
    fun getAppInfo(): AppInfo
}