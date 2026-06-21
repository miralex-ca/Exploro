package com.exploramus.data.assets

import android.content.Context
import com.exploramus.data.common.AssetFileReader


fun createAndroidAssetFileReader(context: Context): AssetFileReader = AndroidAssetFileReader(context)

class AndroidAssetFileReader(
    private val context: Context
) : AssetFileReader {
    override fun readFile(fileName: String): String =
        context.assets
            .open(fileName)
            .bufferedReader()
            .use { it.readText() }
}

