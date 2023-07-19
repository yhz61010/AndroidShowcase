package com.leovp.androidshowcase.ui.main

import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.leovp.androidshowcase.ui.membercenter.MemberCenterScreen
import com.leovp.androidshowcase.ui.theme.AppTheme

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
    startDestination: String = AppDestinations.SPLASH_ROUTE,
) {
    NavHost(navController = navController, startDestination = startDestination, modifier = modifier) {
        composable(AppDestinations.SPLASH_ROUTE) {
            AnimatedSplashScreen(navController = navController)
        }
        composable(AppDestinations.MAIN_ROUTE) {
            AppTheme(dynamicColor = false) {
                MainScreen(widthSize = widthSizeClass, modifier, navController)
            }
        }

        composable(AppDestinations.HOME_ROUTE) {}
        composable(AppDestinations.INTERESTS_ROUTE) {}
        composable(AppDestinations.MY_ROUTE) {}

        composable(DrawerDestinations.VIP_ROUTE) {
            MemberCenterScreen(modifier, navController)
        }
        composable(DrawerDestinations.MESSAGES) {}
        composable(DrawerDestinations.SETTING_ROUTE) {}
        composable(DrawerDestinations.EXIT_ROUTE) {}
    }
}
