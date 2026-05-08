@file:JvmName("LocalDataSourceFactoryForAndroidKt")

package com.muralex.localdb

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import appLocalDb.AppLocalDb
import com.muralex.data.common.LocalDataSource

fun createAndroidSqliteDriver(context: Context) : SqlDriver = AndroidSqliteDriver(AppLocalDb.Schema, context, "applocal.db")

fun createAndroidLocalDataSource(context: Context): LocalDataSource = SQLDelightLocalDataSource(
    createAndroidSqliteDriver(context)
)