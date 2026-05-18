package com.muralex.data.sources.localsettings

import com.russhwolf.settings.Settings
import com.russhwolf.settings.boolean
import com.russhwolf.settings.int
import com.russhwolf.settings.long
import com.russhwolf.settings.string

class MySettings (s : Settings) {
    var listCacheTimestamp by s.long(defaultValue = 0)
    var savedLevel1URI by s.string(defaultValue = "Level1Navigation.AllCountries.screenIdentifier.URI")
    var dbVersion by s.long(defaultValue = 1)
    var shouldForceDbUpdate by s.boolean(defaultValue = false)
    var themeModeId by s.int(defaultValue = 0)
}


enum class ThemeMode(val id: Int, val title: String) {
    LIGHT(0, "Light mode"),
    DARK(1, "Dark mode"),
    AUTO(2, "System default");

    companion object {
        val list = listOf(LIGHT, DARK, AUTO)
        fun fromId(value: Int?): ThemeMode {
            return when (value) {
                DARK.id -> DARK
                LIGHT.id -> LIGHT
                else -> AUTO
            }
        }
    }
}