package com.leovp.androidshowcase.ui.main

import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

/**
 * Author: Michael Leo
 * Date: 2023/7/17 10:24
 */

private const val TAG = "NavGraph"

@Composable
fun AppNavGraph(
    widthSizeClass: WindowWidthSizeClass,
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: String = AppDestinations.HOME_ROUTE,
) {
    NavHost(navController = navController, startDestination = startDestination, modifier = modifier) {
        composable(AppDestinations.HOME_ROUTE) {
            MainScreen(widthSize = widthSizeClass, navController)
        }
        composable(AppDestinations.INTERESTS_ROUTE) {}
        composable(AppDestinations.MY_ROUTE) {}
    }
}
