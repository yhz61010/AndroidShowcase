package com.leovp.androidshowcase.ui.main

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.leovp.androidshowcase.ui.drawerscreens.membercenter.MemberCenterScreen
import com.leovp.androidshowcase.ui.theme.AppTheme
import com.leovp.androidshowcase.ui.theme.ImmersiveTheme
import com.leovp.androidshowcase.ui.theme.immersive_sys_ui

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
    val navigationActions = getNavigationActions(navController = navController)
    NavHost(navController = navController, startDestination = startDestination, modifier = modifier) {
        composable(AppDestinations.SPLASH_ROUTE) {
            // AnimatedSplashScreen(navController = navController)
            SplashScreen {
                navigationActions.navigate(AppDestinations.MAIN_ROUTE)
            }
        }
        composable(AppDestinations.MAIN_ROUTE) {
            AppTheme(dynamicColor = false) {
                MainScreen(
                    widthSize = widthSizeClass,
                    modifier = modifier,
                    navController = navController
                )
            }
        }

        addAppDrawerGraph(onNavigateToRoute = {}, modifier, navController)
    }
}

fun NavGraphBuilder.addAppDrawerGraph(
    onNavigateToRoute: (String) -> Unit,
    modifier: Modifier = Modifier,
    navController: NavHostController,
) {
    composable(DrawerDestinations.MEMBER_CENTER_ROUTE) {
        ImmersiveTheme(color = immersive_sys_ui, lightStatusBar = !isSystemInDarkTheme(), dynamicColor = false) {
            MemberCenterScreen(modifier, navController)
        }
    }
    composable(DrawerDestinations.MESSAGES) {}
    composable(DrawerDestinations.SETTING_ROUTE) {}
    composable(DrawerDestinations.EXIT_ROUTE) {}
}