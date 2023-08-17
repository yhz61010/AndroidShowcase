package com.leovp.androidshowcase.ui.main

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.leovp.androidshowcase.ui.drawerscreens.membercenter.MemberCenterScreen
import com.leovp.androidshowcase.ui.theme.ImmersiveTheme
import com.leovp.androidshowcase.ui.theme.SplashTheme
import com.leovp.androidshowcase.ui.theme.immersive_sys_ui

/**
 * Author: Michael Leo
 * Date: 2023/7/17 10:24
 */

private const val TAG = "NavGraph"

fun NavGraphBuilder.addAppMainGraph(
    widthSizeClass: WindowWidthSizeClass,
    navigationActions: AppNavigationActions,
    modifier: Modifier = Modifier,
) {
    composable(AppDestinations.SPLASH_ROUTE) {
        SplashTheme {
            // AnimatedSplashScreen(navController = navController)
            SplashScreen(onTimeout = { navigationActions.navigate(AppDestinations.MAIN_ROUTE) })
        }
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
}

fun NavGraphBuilder.addAppDrawerGraph(
    widthSizeClass: WindowWidthSizeClass,
    navigationActions: AppNavigationActions,
    modifier: Modifier = Modifier,
) {
    composable(DrawerDestinations.MEMBER_CENTER_ROUTE) {
        ImmersiveTheme(color = immersive_sys_ui, lightStatusBar = !isSystemInDarkTheme(), dynamicColor = false) {
            MemberCenterScreen(
                widthSize = widthSizeClass,
                onMenuUpAction = { navigationActions.upPress() },
                modifier = modifier
            )
        }
    }
    composable(DrawerDestinations.MESSAGES) {}
    composable(DrawerDestinations.SETTING_ROUTE) {}
    composable(DrawerDestinations.EXIT_ROUTE) {}
}