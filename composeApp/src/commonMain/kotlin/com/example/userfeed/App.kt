package com.example.userfeed

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import com.example.userfeed.presentation.screens.splash.SplashScreen
import com.example.userfeed.presentation.theme.UserFeedTheme

@Composable
fun App() {
    UserFeedTheme {
        Navigator(SplashScreen()) { navigator ->
            SlideTransition(navigator)
        }
    }
}
