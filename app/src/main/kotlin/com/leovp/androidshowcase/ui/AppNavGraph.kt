package com.leovp.androidshowcase.ui

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.leovp.android.exts.toast
import com.leovp.androidshowcase.presentation.MainScreen
import com.leovp.androidshowcase.presentation.SplashScreen
import com.leovp.androidshowcase.ui.theme.SplashTheme
import com.leovp.discovery.domain.model.SongModel
import com.leovp.discovery.presentation.comment.CommentScreen
import com.leovp.discovery.presentation.player.PlayerScreen
import com.leovp.discovery.presentation.search.SearchScreen
import com.leovp.feature.base.ui.LocalNavigationActions
import com.leovp.feature.base.ui.Screen
import com.leovp.json.toObject
import com.leovp.log.base.d
import com.leovp.maindrawer.membercenter.presentation.MemberCenterScreen
import com.leovp.ui.theme.ImmersiveTheme
import com.leovp.ui.theme.immersive_sys_ui
import java.net.URLDecoder

/**
 * Author: Michael Leo
 * Date: 2023/7/17 10:24
 */

private const val TAG = "NavGraph"

private val slideUp = AnimatedContentTransitionScope.SlideDirection.Up
private val slideDown = AnimatedContentTransitionScope.SlideDirection.Down
// private val tween = tween<IntOffset>(durationMillis = SCREEN_TRANSITION_DURATION)

fun NavGraphBuilder.addAppMainGraph(widthSizeClass: WindowWidthSizeClass) {
    composable(
        route = Screen.Splash.route,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None },
        popEnterTransition = { EnterTransition.None },
        popExitTransition = { ExitTransition.None },
    ) {
        d(TAG) { "=> Enter Splash Composable <=" }
        SplashTheme {
            // AnimatedSplashScreen(navController = navController)
            val navController = LocalNavigationActions.current
            SplashScreen(
                onTimeout = { navController.navigate(Screen.Main.route) },
            )
        }
    }
    composable(
        route = Screen.Main.route,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None },
        popEnterTransition = { EnterTransition.None },
        popExitTransition = { ExitTransition.None },
    ) {
        d(TAG) { "=> Enter Main Composable <=" }
        ImmersiveTheme(
            systemBarColor = Color.Transparent,
            dynamicColor = false,
            lightSystemBar = true,
        ) {
            MainScreen(widthSize = widthSizeClass)
        }
    }
}

fun NavGraphBuilder.addAppDrawerGraph() {
    d(TAG) { "=> Enter addAppDrawerGraph <=" }
    composable(route = Screen.MemberCenterScreen.route) {
        d(TAG) { "=> Enter MemberCenterScreen Composable <=" }
        ImmersiveTheme(
            systemBarColor = immersive_sys_ui,
            lightSystemBar = !isSystemInDarkTheme(),
            dynamicColor = false,
        ) {
            MemberCenterScreen()
        }
    }
    composable(route = Screen.MessageScreen.route) {
        d(TAG) { "=> Enter MessageScreen Composable <=" }
    }
    composable(route = Screen.SettingScreen.route) {
        d(TAG) { "=> Enter SettingScreen Composable <=" }
    }
}

fun NavGraphBuilder.addPlayerGraph() {
    d(TAG) { "=> Enter addOtherGraph <=" }

    composable(route = Screen.SearchScreen.route) {
        d(TAG) { "=> Enter SearchScreen Composable <=" }
        ImmersiveTheme(
            systemBarColor = Color.Transparent,
            dynamicColor = false,
            lightSystemBar = true,
        ) {
            // val mainViewModel = hiltViewModel<MainViewModel>()
            // val discoveryViewModel = hiltViewModel<DiscoveryViewModel>()
            SearchScreen(
                // widthSize = widthSizeClass,
                // onNavigationToDrawerItem = { drawerItemRoute: String ->
                //     navigationActions.navigate(drawerItemRoute)
                // },
                // mainViewModel = mainViewModel,
                // discoveryViewModel = discoveryViewModel
            )
        }
    }

    composable(
        route = Screen.PlayerScreen.route,
        arguments =
            listOf(
                navArgument("id") { type = NavType.LongType },
                navArgument("artist") { type = NavType.StringType },
                navArgument("track") { type = NavType.StringType },
            ),
        enterTransition = {
            slideIntoContainer(towards = slideUp, animationSpec = tween(350))
        },
        popExitTransition = {
            slideOutOfContainer(towards = slideDown, animationSpec = tween(800))
        },
    ) {
        d(TAG) { "=> Enter PlayerScreen Composable <=" }
        val ctx = LocalContext.current
        // val artist = it.arguments?.getString("artist")
        // val track = it.arguments?.getString("track")
        // requireNotNull(artist) { "artist can not be null for Player Screen." }
        // requireNotNull(track) { "track can not be null for Player Screen." }

        // val ids: Array<Long> =
        //     it.arguments?.getLongArray("ids")?.toTypedArray() ?: emptyArray<Long>()
        // require(ids.isNotEmpty()) { "The parameter ids can't be empty." }
        // d(TAG) { "route: ${it.destination.route}  ids=$ids  artist=$artist  track=$track" }
        ImmersiveTheme(
            systemBarColor = Color.Transparent,
            dynamicColor = false,
            lightSystemBar = false,
        ) {
            PlayerScreen(
                // widthSize = widthSizeClass,
                // onNavigationToDrawerItem = { drawerItemRoute: String ->
                //     navigationActions.navigate(drawerItemRoute)
                // },
                // mainViewModel = mainViewModel,
                // discoveryViewModel = discoveryViewModel
                onShareAction = { ctx.toast("Click [Share] button") },
            )
        }
    }
}

fun NavGraphBuilder.addCommentGraph(
    // widthSizeClass: WindowWidthSizeClass,
) {
    d(TAG) { "=> Enter addCommentGraph <=" }
    composable(
        route = Screen.CommentScreen.route,
        arguments = listOf(navArgument("songInfo") { type = NavType.StringType }),
    ) {
        val songInfoString = checkNotNull(it.arguments?.getString("songInfo"))
        val songInfo =
            URLDecoder.decode(songInfoString, Charsets.UTF_8.name()).toObject<SongModel>()

        d(TAG) { "=> Enter CommentScreen Composable <=" }
        ImmersiveTheme(
            systemBarColor = immersive_sys_ui,
            lightSystemBar = !isSystemInDarkTheme(),
            dynamicColor = false,
        ) {
            CommentScreen(songInfo = checkNotNull(songInfo))
        }
    }
}
