package com.leovp.androidshowcase.ui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.leovp.androidshowcase.presentation.MainScreen
import com.leovp.androidshowcase.presentation.MainViewModel
import com.leovp.androidshowcase.presentation.SplashScreen
import com.leovp.androidshowcase.ui.theme.ImmersiveTheme
import com.leovp.androidshowcase.ui.theme.SplashTheme
import com.leovp.androidshowcase.ui.theme.immersive_sys_ui
import com.leovp.feature_discovery.presentation.DiscoveryViewModel
import com.leovp.feature_main_drawer.membercenter.presentation.MemberCenterScreen
import com.leovp.module.common.log.d

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
    d(TAG) { "=> Enter addAppMainGraph <=" }
    composable(route = Screen.Splash.route) {
        SplashTheme {
            // AnimatedSplashScreen(navController = navController)
            SplashScreen(onTimeout = { navigationActions.navigate(Screen.Main.route) })
        }
    }
    composable(route = Screen.Main.route) {
        ImmersiveTheme(
            systemBarColor = Color.Transparent, dynamicColor = false, lightSystemBar = true
        ) {
            val mainViewModel = hiltViewModel<MainViewModel>()
            val discoveryViewModel = hiltViewModel<DiscoveryViewModel>()
            val mainUiState = mainViewModel.uiState.collectAsStateWithLifecycle()
            val discoveryUiState = discoveryViewModel.uiState.collectAsStateWithLifecycle()
            MainScreen(
                modifier = modifier,
                widthSize = widthSizeClass,
                onSearchBarClick = { navigationActions.navigate(Screen.SearchScreen.route) },
                onNavigationToDrawerItem = { drawerItemRoute: String ->
                    navigationActions.navigate(drawerItemRoute)
                },
                mainUiState = mainUiState.value,
                discoveryUiState = discoveryUiState.value,
                onDiscoveryRefresh = {
                    discoveryViewModel.refreshAll()
                    mainViewModel.refreshAll()
                },
            )
        }
    }
}

fun NavGraphBuilder.addAppDrawerGraph(
    // widthSizeClass: WindowWidthSizeClass,
    navigationActions: AppNavigationActions,
    modifier: Modifier = Modifier,
) {
    d(TAG) { "=> Enter addAppDrawerGraph <=" }
    composable(route = Screen.MemberCenterScreen.route) {
        ImmersiveTheme(
            systemBarColor = immersive_sys_ui,
            lightSystemBar = !isSystemInDarkTheme(),
            dynamicColor = false
        ) {
            MemberCenterScreen(
                // widthSize = widthSizeClass,
                onMenuUpAction = { navigationActions.upPress() }, modifier = modifier
            )
        }
    }
    composable(route = Screen.MessageScreen.route) {}
    composable(route = Screen.SettingScreen.route) {}
}
