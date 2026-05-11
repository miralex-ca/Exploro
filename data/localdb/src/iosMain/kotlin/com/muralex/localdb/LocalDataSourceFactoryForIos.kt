package com.muralex.localdb

import app.cash.sqldelight.driver.native.NativeSqliteDriver
import appLocalDb.AppLocalDb
import com.muralex.data.common.LocalDataSource


fun createIosLocalDataSource(): LocalDataSource =
    createLocalDataSource(NativeSqliteDriver(AppLocalDb.Schema, DatabaseConfig.NAME))

