package com.exploramus.assets

import com.muralex.data.common.AssetFileReader
import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSBundle
import platform.Foundation.NSString
import platform.Foundation.NSUTF8StringEncoding
import platform.Foundation.stringWithContentsOfFile

fun createIOSAssetFileReader(): AssetFileReader = IOSAssetFileReader()

@OptIn(ExperimentalForeignApi::class)
class IOSAssetFileReader : AssetFileReader {
    override fun readFile(fileName: String): String {
        val name = fileName.substringBeforeLast(".")
        val ext = fileName.substringAfterLast(".")
        val path = NSBundle.mainBundle.pathForResource(name, ext)
            ?: error("Asset file not found: $fileName")
        return NSString.stringWithContentsOfFile(path, NSUTF8StringEncoding, null)
            ?: error("Could not read asset file: $fileName")
    }
}

