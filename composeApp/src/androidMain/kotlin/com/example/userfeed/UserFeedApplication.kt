package com.example.userfeed

import android.app.Application
import com.example.userfeed.core.di.platformModule
import com.example.userfeed.core.di.sharedModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class UserFeedApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@UserFeedApplication)
            modules(sharedModules + platformModule)
        }
    }
}
