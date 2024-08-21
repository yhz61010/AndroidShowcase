package com.leovp.androidshowcase.ui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.leovp.androidshowcase.presentation.MainScreen
import com.leovp.androidshowcase.presentation.MainViewModel
import com.leovp.androidshowcase.presentation.SplashScreen
import com.leovp.androidshowcase.ui.theme.ImmersiveTheme
import com.leovp.androidshowcase.ui.theme.SplashTheme
import com.leovp.ui.theme.immersive_sys_ui
import com.leovp.feature_discovery.presentation.DiscoveryViewModel
import com.leovp.feature_discovery.presentation.PlayerScreen
import com.leovp.feature_discovery.presentation.SearchScreen
import com.leovp.feature_main_drawer.membercenter.presentation.MemberCenterScreen
import com.leovp.module.common.log.d
import java.net.URLEncoder

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
            // val context = LocalContext.current
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
                onPersonalItemClick = { data ->
                    val encodedTitle = URLEncoder.encode(data.title, "UTF-8")
                    navigationActions.navigate(Screen.PlayerScreen.routeName, "${data.id}/$encodedTitle")
                }
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

fun NavGraphBuilder.addOtherGraph(navigationActions: AppNavigationActions) {
    d(TAG) { "=> Enter addOtherGraph <=" }
    composable(route = Screen.SearchScreen.route) {
        ImmersiveTheme(
            systemBarColor = Color.Transparent,
            dynamicColor = false,
            lightSystemBar = true,
        ) {
            // val mainViewModel = hiltViewModel<MainViewModel>()
            // val discoveryViewModel = hiltViewModel<DiscoveryViewModel>()
            SearchScreen(
                modifier = Modifier,
                // widthSize = widthSizeClass,
                // onNavigationToDrawerItem = { drawerItemRoute: String ->
                //     navigationActions.navigate(drawerItemRoute)
                // },
                // mainViewModel = mainViewModel,
                // discoveryViewModel = discoveryViewModel
                onMenuUpAction = { navigationActions.upPress() }
            )
        }
    }

    composable(
        route = Screen.PlayerScreen.route,
        arguments = listOf(
            navArgument("id") { type = NavType.LongType },
            navArgument("title") { type = NavType.StringType },
        )
    ) {
        val id = it.arguments?.getLong("id")
        val title = it.arguments?.getString("title")
        d(TAG) { "route: ${it.destination.route}  id=$id  title=$title" }
        ImmersiveTheme(
            systemBarColor = Color.Transparent,
            dynamicColor = false,
            lightSystemBar = true,
        ) {
            // val mainViewModel = hiltViewModel<MainViewModel>()
            // val discoveryViewModel = hiltViewModel<DiscoveryViewModel>()
            PlayerScreen(
                topBarTitle = title,
                // widthSize = widthSizeClass,
                // onNavigationToDrawerItem = { drawerItemRoute: String ->
                //     navigationActions.navigate(drawerItemRoute)
                // },
                // mainViewModel = mainViewModel,
                // discoveryViewModel = discoveryViewModel
                onMenuUpAction = { navigationActions.upPress() },
                modifier = Modifier
            )
        }
    }
}
