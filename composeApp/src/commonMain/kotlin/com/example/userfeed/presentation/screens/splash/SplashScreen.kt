package com.example.userfeed.presentation.screens.splash

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.userfeed.presentation.screens.userlist.UserListScreen
import com.example.userfeed.presentation.theme.AppColor
import com.example.userfeed.presentation.theme.AppTextStyle

class SplashScreen : Screen {

    @Composable
    override fun Content() {
        val screenModel = koinScreenModel<SplashScreenModel>()
        val state by screenModel.state.collectAsState()
        val navigator = LocalNavigator.currentOrThrow

        when (val currentState = state) {
            is SplashState.Loading -> SplashLoadingContent()
            is SplashState.NavigateToUsers -> navigator.replace(UserListScreen())
            is SplashState.Error -> {
                SplashErrorContent(
                    message = currentState.message,
                    onRetry = screenModel::retry
                )
            }
        }
    }
}

@Composable
private fun SplashLoadingContent() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "UserFeed",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = AppColor.primary
        )
        Spacer(modifier = Modifier.height(24.dp))
        CircularProgressIndicator(modifier = Modifier.size(48.dp))
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Loading...",
            style = AppTextStyle.bodyMedium,
            color = AppColor.onSurfaceVariant
        )
    }
}

@Composable
private fun SplashErrorContent(message: String, onRetry: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "UserFeed",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = AppColor.primary
        )
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = message,
            style = AppTextStyle.bodyMedium,
            color = AppColor.error
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onRetry) {
            Text("Retry")
        }
    }
}
