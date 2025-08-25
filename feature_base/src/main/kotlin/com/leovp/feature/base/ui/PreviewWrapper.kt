package com.leovp.feature.base.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.rememberNavController
import com.leovp.compose.utils.previewInitLog
import com.leovp.ui.theme.ImmersiveTheme

/**
 * Author: Michael Leo
 * Date: 2025/8/26 13:56
 */

@Composable
fun PreviewWrapper(
    content: @Composable () -> Unit
) {
    PreviewWrapperNoTheme {
        ImmersiveTheme(
            systemBarColor = Color.Transparent,
            dynamicColor = false,
            lightSystemBar = false,
        ) {
            content()
        }
    }
}

@Composable
fun PreviewWrapperNoTheme(
    content: @Composable () -> Unit
) {
    previewInitLog()

    val navController = rememberNavController()
    val navigationActions =
        rememberNavigationActions(navController = navController)

    CompositionLocalProvider(
        LocalNavigationActions provides navigationActions,
    ) {
        content()
    }
}
