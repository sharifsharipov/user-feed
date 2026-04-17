package com.example.userfeed.presentation.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import com.example.userfeed.presentation.theme.AppColor
import com.example.userfeed.presentation.theme.AppTextStyle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(
    title: String,
    onBackClick: (() -> Unit)? = null,
    actions: @Composable () -> Unit = {}
) {
    TopAppBar(
        title = { Text(title) },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = AppColor.primaryContainer,
            titleContentColor = AppColor.onPrimaryContainer
        ),
        navigationIcon = {
            if (onBackClick != null) {
                IconButton(onClick = onBackClick) {
                    Text(text = "\u2190", style = AppTextStyle.titleLarge)
                }
            }
        },
        actions = { actions() }
    )
}
