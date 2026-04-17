package com.example.userfeed.core.di

import com.example.userfeed.core.database.DatabaseDriverFactory
import com.example.userfeed.db.UserFeedDatabase
import org.koin.dsl.module

actual val platformModule = module {
    single { DatabaseDriverFactory(get()) }
    single { UserFeedDatabase(get<DatabaseDriverFactory>().createDriver()) }
}
