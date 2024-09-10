package com.leovp.androidshowcase.ui

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.IntOffset
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.leovp.android.exts.toast
import com.leovp.androidshowcase.presentation.MainScreen
import com.leovp.androidshowcase.presentation.MainViewModel
import com.leovp.androidshowcase.presentation.SCREEN_TRANSITION_DURATION
import com.leovp.androidshowcase.presentation.SplashScreen
import com.leovp.androidshowcase.ui.theme.SplashTheme
import com.leovp.feature_discovery.presentation.DiscoveryViewModel
import com.leovp.feature_discovery.presentation.PlayerScreen
import com.leovp.feature_discovery.presentation.PlayerViewModel
import com.leovp.feature_discovery.presentation.SearchScreen
import com.leovp.feature_main_drawer.membercenter.presentation.MemberCenterScreen
import com.leovp.module.common.log.d
import com.leovp.module.common.log.i
import com.leovp.ui.theme.ImmersiveTheme
import com.leovp.ui.theme.immersive_sys_ui
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
    composable(
        route = Screen.Splash.route,
        enterTransition = { fadeIn() },
        exitTransition = { fadeOut() },
        popEnterTransition = { fadeIn() },
        popExitTransition = { fadeOut() },
    ) {
        SplashTheme {
            // AnimatedSplashScreen(navController = navController)
            SplashScreen(onTimeout = { navigationActions.navigate(Screen.Main.route) })
        }
    }
    composable(
        route = Screen.Main.route,
        enterTransition = { fadeIn() },
        exitTransition = { fadeOut() },
        popEnterTransition = { fadeIn() },
        popExitTransition = { fadeOut() },
    ) {
        ImmersiveTheme(
            systemBarColor = Color.Transparent, dynamicColor = false, lightSystemBar = true
        ) {
            // val context = LocalContext.current
            val mainViewModel = hiltViewModel<MainViewModel>()
            val discoveryViewModel = hiltViewModel<DiscoveryViewModel>()
            val mainUiState = mainViewModel.uiState
            val discoveryUiState = discoveryViewModel.uiState
            MainScreen(
                modifier = modifier,
                widthSize = widthSizeClass,
                onSearchBarClick = { navigationActions.navigate(Screen.SearchScreen.route) },
                onNavigationToDrawerItem = { drawerItemRoute: String ->
                    navigationActions.navigate(drawerItemRoute)
                },
                mainUiStateFlow = mainUiState,
                discoveryUiStateFlow = discoveryUiState,
                onDiscoveryRefresh = {
                    discoveryViewModel.refreshAll()
                    mainViewModel.refreshAll()
                },
                onPersonalItemClick = { data ->
                    val artist = URLEncoder.encode(data.subTitle, "UTF-8")
                    val track = URLEncoder.encode(data.title, "UTF-8")
                    i(TAG) { "Click [Personal Item] artist=$artist  track=$track" }
                    navigationActions.navigate(
                        Screen.PlayerScreen.routeName,
                        "$artist/$track",
                    )
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

    val slideStart = AnimatedContentTransitionScope.SlideDirection.Up
    val slideEnd = AnimatedContentTransitionScope.SlideDirection.Down
    val tween = tween<IntOffset>(
        durationMillis = SCREEN_TRANSITION_DURATION,
        easing = LinearOutSlowInEasing,
    )
    composable(
        route = Screen.PlayerScreen.route,
        arguments = listOf(
            navArgument("artist") { type = NavType.StringType },
            navArgument("track") { type = NavType.StringType },
        ),
        enterTransition = { slideIntoContainer(slideStart, tween) },
        exitTransition = { fadeOut() },
        popEnterTransition = { fadeIn() },
        popExitTransition = { slideOutOfContainer(slideEnd, tween) },
    ) {
        val ctx = LocalContext.current
        val artist = it.arguments?.getString("artist")
        val track = it.arguments?.getString("track")
        requireNotNull(artist) { "artist can not be null for Player Screen." }
        requireNotNull(track) { "track can not be null for Player Screen." }
        d(TAG) { "route: ${it.destination.route}  artist=$artist  track=$track" }
        ImmersiveTheme(
            systemBarColor = Color.Transparent,
            dynamicColor = false,
            lightSystemBar = true,
        ) {
            val playerViewModel = hiltViewModel<PlayerViewModel>()
            PlayerScreen(
                viewModel = playerViewModel,
                artist = artist,
                track = track,
                // widthSize = widthSizeClass,
                // onNavigationToDrawerItem = { drawerItemRoute: String ->
                //     navigationActions.navigate(drawerItemRoute)
                // },
                // mainViewModel = mainViewModel,
                // discoveryViewModel = discoveryViewModel
                onMenuUpAction = { navigationActions.upPress() },
                onShareAction = { ctx.toast("Click [Share] button") },
                modifier = Modifier
            )
        }
    }
}
