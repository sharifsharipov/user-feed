package com.example.userfeed.core.database

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import com.example.userfeed.db.UserFeedDatabase

actual class DatabaseDriverFactory {
    actual fun createDriver(): SqlDriver {
        return NativeSqliteDriver(UserFeedDatabase.Schema, "userfeed.db")
    }
}
