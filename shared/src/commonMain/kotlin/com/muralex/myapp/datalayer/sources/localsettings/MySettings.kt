package com.muralex.myapp.datalayer.sources.localsettings

import com.muralex.myapp.viewmodel.screens.Level1Navigation
import com.russhwolf.settings.Settings
import com.russhwolf.settings.long
import com.russhwolf.settings.string

class MySettings (s : Settings) {


    // here we define all our local settings properties,
    // by using the MultiplatformSettings library delegated properties

    var listCacheTimestamp by s.long(defaultValue = 0)
    var savedLevel1URI by s.string(defaultValue = Level1Navigation.AllCountries.screenIdentifier.URI)


}