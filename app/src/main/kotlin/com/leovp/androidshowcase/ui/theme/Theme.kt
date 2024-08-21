package com.leovp.androidshowcase.ui.theme

import android.app.Activity
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.colorResource
import androidx.core.view.WindowCompat
import com.leovp.androidshowcase.R
import com.leovp.ui.theme.Typography
import com.leovp.ui.theme.lightColorsTheme

@Composable
fun SplashTheme(content: @Composable () -> Unit) {
    val view = LocalView.current
    val systemBarColor = colorResource(id = R.color.app_windowBackground)
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = systemBarColor.toArgb()
            window.navigationBarColor = systemBarColor.toArgb()
            val isLightBar = false
            with(WindowCompat.getInsetsController(window, view)) {
                isAppearanceLightStatusBars = isLightBar
                isAppearanceLightNavigationBars = isLightBar
            }
        }
    }

    MaterialTheme(
        colorScheme = lightColorsTheme, typography = Typography, content = content
    )
}
