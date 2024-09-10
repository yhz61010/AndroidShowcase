package com.leovp.androidshowcase.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.IntOffset
import androidx.core.view.WindowCompat
import androidx.navigation.NavBackStackEntry
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.leovp.androidshowcase.ui.Screen
import com.leovp.androidshowcase.ui.addAppDrawerGraph
import com.leovp.androidshowcase.ui.addAppMainGraph
import com.leovp.androidshowcase.ui.addOtherGraph
import com.leovp.androidshowcase.ui.rememberNavigationActions
import com.leovp.androidshowcase.utils.InitManager
import com.leovp.module.common.GlobalConst
import com.leovp.module.common.http.RequestUtil
import com.leovp.module.common.log.d
import dagger.hilt.android.AndroidEntryPoint

/**
 * Author: Michael Leo
 * Date: 2023/7/14 13:07
 */

private const val TAG = "MA"

typealias EnterTransitionFunc =
        AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition

typealias ExitTransitionFunc =
        AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition

const val SCREEN_TRANSITION_DURATION = 300

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        InitManager.init(application)
        d(TAG) { "=> Enter MainActivity <=" }

        RequestUtil.initNetEngine(baseUrl = GlobalConst.API_BASE_URL)

        // This app draws behind the system bars, so we want to handle fitting system windows
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            val widthSizeClass = calculateWindowSizeClass(this).widthSizeClass
            ShowcaseApp(widthSizeClass)
        }
    }
}

@Composable
fun ShowcaseApp(widthSizeClass: WindowWidthSizeClass) {
    d(TAG) { "=> Enter ShowcaseApp <=" }
    val navController = rememberNavController()
    val navigationActions = rememberNavigationActions(navController = navController)

    /*
     * Suppose we are moving from screen A to B. Then,
     * - Screen B will run enterTransition
     * - Screen A will run exitTransition
     *
     * Suppose we press the back button. I.e, popBackStack.
     * - Screen B will runs popExitTransition
     * - Screen A will run popEnterTransition
     */
    // val slideStart = AnimatedContentTransitionScope.SlideDirection.Start
    // val slideEnd = AnimatedContentTransitionScope.SlideDirection.End
    val tween = tween<IntOffset>(
        durationMillis = SCREEN_TRANSITION_DURATION,
        easing = LinearOutSlowInEasing,
    )
    val enterTransition: EnterTransitionFunc = {
        // slideIntoContainer(slideStart, tween)
        slideInHorizontally(animationSpec = tween, initialOffsetX = { it })
    }
    val exitTransition: ExitTransitionFunc = {
        // slideOutOfContainer(slideStart, tween)
        slideOutHorizontally(animationSpec = tween, targetOffsetX = { -it / 3 })
    }
    val popEnterTransition: EnterTransitionFunc = {
        // slideIntoContainer(slideEnd, tween)
        slideInHorizontally(animationSpec = tween, initialOffsetX = { -it / 3 })
    }
    val popExitTransition: ExitTransitionFunc = {
        // slideOutOfContainer(slideEnd, tween)
        slideOutHorizontally(animationSpec = tween, targetOffsetX = { it })
    }

    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route,
        modifier = Modifier,
        enterTransition = enterTransition,
        exitTransition = exitTransition,
        popEnterTransition = popEnterTransition,
        popExitTransition = popExitTransition,
    ) {
        addAppMainGraph(
            widthSizeClass = widthSizeClass,
            navigationActions = navigationActions,
        )
        /* widthSizeClass = widthSizeClass, */
        addAppDrawerGraph(navigationActions = navigationActions)
        addOtherGraph(navigationActions = navigationActions)
    }
}