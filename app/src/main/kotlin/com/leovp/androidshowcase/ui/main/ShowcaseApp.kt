package com.leovp.androidshowcase.ui.main

import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController

/**
 * Author: Michael Leo
 * Date: 2023/7/17 09:31
 */

private const val TAG = "SC-App"

@Composable
fun ShowcaseApp(widthSizeClass: WindowWidthSizeClass) {
    val navController = rememberNavController()
    val navigationActions = rememberNavigationActions(navController = navController)
    val modifier = Modifier
    NavHost(
        navController = navController,
        startDestination = AppDestinations.SPLASH_ROUTE,
        modifier = modifier
    ) {
        addAppMainGraph(
            widthSizeClass = widthSizeClass,
            navigationActions = navigationActions,
            modifier = modifier
        )

        addAppDrawerGraph(
            widthSizeClass = widthSizeClass,
            navigationActions = navigationActions,
            modifier = modifier
        )
    }
}