package com.leovp.androidshowcase

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.leovp.androidshowcase.presentation.MainScreen
import com.leovp.androidshowcase.presentation.SplashScreen
import com.leovp.feature_main_drawer.membercenter.MemberCenterScreen
import com.leovp.androidshowcase.ui.theme.ImmersiveTheme
import com.leovp.androidshowcase.ui.theme.SplashTheme
import com.leovp.androidshowcase.ui.theme.immersive_sys_ui

/**
 * Author: Michael Leo
 * Date: 2023/7/17 10:24
 */

// private const val TAG = "NavGraph"

fun NavGraphBuilder.addAppMainGraph(
    widthSizeClass: WindowWidthSizeClass,
    navigationActions: AppNavigationActions,
    modifier: Modifier = Modifier,
) {
    composable(route = Screen.Splash.route) {
        SplashTheme {
            // AnimatedSplashScreen(navController = navController)
            SplashScreen(onTimeout = { navigationActions.navigate(Screen.Main.route) })
        }
    }
    composable(route = Screen.Main.route) {
        ImmersiveTheme(
            systemBarColor = Color.Transparent,
            dynamicColor = false,
            lightSystemBar = true
        ) {
            MainScreen(
                widthSize = widthSizeClass,
                onNavigationToDrawerItem = { drawerItemRoute: String ->
                    navigationActions.navigate(drawerItemRoute)
                },
                modifier = modifier
            )
        }
    }
}

fun NavGraphBuilder.addAppDrawerGraph(
    // widthSizeClass: WindowWidthSizeClass,
    navigationActions: AppNavigationActions,
    modifier: Modifier = Modifier,
) {
    composable(route = Screen.MemberCenterScreen.route) {
        ImmersiveTheme(systemBarColor = immersive_sys_ui, lightSystemBar = !isSystemInDarkTheme(), dynamicColor = false) {
            MemberCenterScreen(
                // widthSize = widthSizeClass,
                onMenuUpAction = { navigationActions.upPress() },
                modifier = modifier
            )
        }
    }
    composable(route = Screen.MessageScreen.route) {}
    composable(route = Screen.SettingScreen.route) {}
}