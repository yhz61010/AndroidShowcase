package com.leovp.androidshowcase.ui.main

import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.leovp.androidshowcase.ui.theme.SplashTheme

/**
 * Author: Michael Leo
 * Date: 2023/7/17 09:31
 */

private const val TAG = "SC-App"

@Composable
fun ShowcaseApp(widthSizeClass: WindowWidthSizeClass) {
    SplashTheme {
        val navController = rememberNavController()
        AppNavGraph(
            widthSizeClass,
            navController = navController,
        )
    }
}
