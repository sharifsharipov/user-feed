package com.example.userfeed

import androidx.compose.ui.window.ComposeUIViewController
import com.example.userfeed.core.di.platformModule
import com.example.userfeed.core.di.sharedModules
import org.koin.core.context.startKoin

fun MainViewController() = ComposeUIViewController(
    configure = {
        startKoin {
            modules(sharedModules + platformModule)
        }
    }
) {
    App()
}
