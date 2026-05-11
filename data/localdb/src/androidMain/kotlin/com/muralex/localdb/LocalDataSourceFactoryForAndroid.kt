@file:JvmName("LocalDataSourceFactoryForAndroidKt")

package com.muralex.localdb

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import appLocalDb.AppLocalDb
import com.muralex.data.common.LocalDataSource

fun createAndroidLocalDataSource(context: Context): LocalDataSource =
    createLocalDataSource(AndroidSqliteDriver(AppLocalDb.Schema, context, DatabaseConfig.NAME))
