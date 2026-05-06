package com.muralex.data.sources.localsettings

import com.russhwolf.settings.Settings
import com.russhwolf.settings.long
import com.russhwolf.settings.string

class MySettings (s : Settings) {
    var listCacheTimestamp by s.long(defaultValue = 0)
    var savedLevel1URI by s.string(defaultValue = "Level1Navigation.AllCountries.screenIdentifier.URI")

}