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
import com.leovp.androidshowcase.presentation.SCREEN_TRANSITION_DURATION
import com.leovp.androidshowcase.presentation.SplashScreen
import com.leovp.androidshowcase.ui.theme.SplashTheme
import com.leovp.feature_discovery.presentation.PlayerScreen
import com.leovp.feature_discovery.presentation.PlayerViewModel
import com.leovp.feature_discovery.presentation.SearchScreen
import com.leovp.feature_main_drawer.membercenter.presentation.MemberCenterScreen
import com.leovp.log.base.d
import com.leovp.ui.theme.ImmersiveTheme
import com.leovp.ui.theme.immersive_sys_ui

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
            MainScreen(
                navigationActions = navigationActions,
                widthSize = widthSizeClass,
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
            navArgument("ids") { type = NavType.LongArrayType },
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

        val ids: Array<Long> =
            it.arguments?.getLongArray("ids")?.toTypedArray() ?: emptyArray<Long>()
        require(ids.isNotEmpty()) { "The parameter ids can't be empty." }
        d(TAG) { "route: ${it.destination.route}  ids=$ids  artist=$artist  track=$track" }
        ImmersiveTheme(
            systemBarColor = Color.Transparent,
            dynamicColor = false,
            lightSystemBar = false,
        ) {
            PlayerScreen(
                ids = ids,
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
