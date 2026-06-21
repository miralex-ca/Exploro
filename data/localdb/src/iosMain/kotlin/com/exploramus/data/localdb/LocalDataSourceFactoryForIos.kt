package com.exploramus.data.localdb

import app.cash.sqldelight.driver.native.NativeSqliteDriver
import appLocalDb.AppLocalDb
import com.exploramus.data.common.LocalDataSource


fun createIosLocalDataSource(): LocalDataSource =
    createLocalDataSource(NativeSqliteDriver(AppLocalDb.Schema, DatabaseConfig.NAME))

