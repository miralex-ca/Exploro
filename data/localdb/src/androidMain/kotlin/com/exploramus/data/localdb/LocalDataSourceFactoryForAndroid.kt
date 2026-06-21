@file:JvmName("LocalDataSourceFactoryForAndroidKt")

package com.exploramus.data.localdb

import android.content.Context
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import appLocalDb.AppLocalDb
import com.exploramus.data.common.LocalDataSource

fun createAndroidLocalDataSource(context: Context): LocalDataSource =
    createLocalDataSource(AndroidSqliteDriver(AppLocalDb.Schema, context, DatabaseConfig.NAME))
