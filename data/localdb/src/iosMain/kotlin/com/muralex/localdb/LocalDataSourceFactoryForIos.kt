package com.muralex.localdb

import app.cash.sqldelight.driver.native.NativeSqliteDriver
import appLocalDb.AppLocalDb
import com.muralex.data.common.LocalDataSource

fun createIosLocalDataSource(): LocalDataSource = SQLDelightLocalDataSource(
    NativeSqliteDriver(AppLocalDb.Schema, "applocal.db")
)

