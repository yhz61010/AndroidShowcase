package com.leovp.androidshowcase.ui.main

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.leovp.androidshowcase.ui.drawerscreens.membercenter.MemberCenterScreen
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
    navController: NavHostController,
    modifier: Modifier = Modifier,
    startDestination: String = AppDestinations.SPLASH_ROUTE,
) {
    val navigationActions = rememberNavigationActions(navController = navController)
    NavHost(navController = navController, startDestination = startDestination, modifier = modifier) {
        composable(AppDestinations.SPLASH_ROUTE) {
            // AnimatedSplashScreen(navController = navController)
            SplashScreen(
                onTimeout = { navigationActions.navigate(AppDestinations.MAIN_ROUTE) }
            )
        }
        composable(AppDestinations.MAIN_ROUTE) {
            ImmersiveTheme(
                color = Color.Transparent,
                dynamicColor = false,
                lightStatusBar = true
            ) {
                MainScreen(
                    widthSize = widthSizeClass,
                    onNavigationToDrawerItem = { drawerItemRoute ->
                        navigationActions.navigate(drawerItemRoute)
                    },
                    modifier = modifier
                )
            }
        }

        addAppDrawerGraph(
            onMenuUpAction = { navigationActions.upPress() },
            modifier = modifier
        )
    }
}

fun NavGraphBuilder.addAppDrawerGraph(
    onMenuUpAction: () -> Unit,
    modifier: Modifier = Modifier,
) {
    composable(DrawerDestinations.MEMBER_CENTER_ROUTE) {
        ImmersiveTheme(color = immersive_sys_ui, lightStatusBar = !isSystemInDarkTheme(), dynamicColor = false) {
            MemberCenterScreen(
                onMenuUpAction = onMenuUpAction,
                modifier = modifier
            )
        }
    }
    composable(DrawerDestinations.MESSAGES) {}
    composable(DrawerDestinations.SETTING_ROUTE) {}
    composable(DrawerDestinations.EXIT_ROUTE) {}
}