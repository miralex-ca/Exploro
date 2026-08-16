package com.exploramus.app.composables.components

import androidx.compose.runtime.Composable
import platform.Foundation.NSBundle
import platform.Foundation.NSLog

@Composable
actual fun flagAssetUri(countryCode: String): String {
    val name = countryCode.lowercase()
    // Based on the project structure and PBXFileSystemSynchronizedRootGroup in Xcode 16,
    // assets in AppRawData/flags/w640 are likely flattened into the bundle root.
    val path = NSBundle.mainBundle.pathForResource(name, "png")
    
    return if (path != null) {
        "file://$path"
    } else {
        NSLog("WARNING: Flag asset not found for country code: $countryCode")
        ""
    }
}
