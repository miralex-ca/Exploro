package com.muralex.data

import android.content.Context
import com.muralex.localdb.createAndroidLocalDataSource

fun createAndroidRepository(context: Context): Repository {
    val localDb = createAndroidLocalDataSource(context)
    return Repository(localDb = localDb)
}
