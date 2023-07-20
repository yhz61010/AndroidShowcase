package com.leovp.androidshowcase.ui.main

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import com.leovp.androidshowcase.R
import com.leovp.kotlin.utils.SingletonHolder
import com.leovp.log.LogContext

/**
 * Author: Michael Leo
 * Date: 2023/7/17 09:59
 */

private const val TAG = "Nav"

// --------------------
// Destinations
// --------------------

object AppDestinations {
    const val SPLASH_ROUTE = "app_splash"
    const val MAIN_ROUTE = "app_main"
}

object DrawerDestinations {
    const val NO_ROUTE = "drawer_no"
    const val PROFILE_ROUTE = "drawer_profile"
    const val MEMBER_CENTER_ROUTE = "drawer_member_center"
    const val MESSAGES = "drawer_messages"
    const val SETTING_ROUTE = "drawer_setting"
    const val EXIT_ROUTE = "drawer_exit"
}

// ----------

sealed class Screen(val route: String, @StringRes val resId: Int) {
    object Home : Screen("app_home", R.string.app_main_tab_home)
    object Interests : Screen("app_interests", R.string.app_main_tab_interests)
    object My : Screen("app_my", R.string.app_main_tab_my)
}

enum class AppBottomNavigationItems(val screen: Screen, var icon: ImageVector) {
    HOME(Screen.Home, Icons.Outlined.Home),
    INTERESTS(Screen.Interests, Icons.Outlined.FavoriteBorder),
    MY(Screen.My, Icons.Outlined.Person)
}

/**
 * Models the navigation actions in the app.
 */
class AppNavigationActions private constructor(private val navController: NavHostController) {
    companion object : SingletonHolder<AppNavigationActions, NavHostController>(::AppNavigationActions)

    val currentRoute: String? get() = navController.currentDestination?.route

    fun upPress() {
        navController.navigateUp()
    }

    fun navigate(route: String) {
        LogContext.log.i(TAG, "-> navigate to: $route")
        outputGraphInfo(route, navController)
        return when (route) {
            AppDestinations.MAIN_ROUTE -> navigationToMain()

            DrawerDestinations.MEMBER_CENTER_ROUTE,
            DrawerDestinations.MESSAGES,
            DrawerDestinations.SETTING_ROUTE,
            DrawerDestinations.EXIT_ROUTE -> navigateToAppDrawerRoute(route)

            else -> error("Illegal route: $route")
        }
    }

    private fun navigationToMain() {
        navController.navigate(AppDestinations.MAIN_ROUTE) {
            // Pop up to the start destination of the graph to
            // avoid building up a large stack of destinations
            // on the back stack as users select items
            //
            // navController.graph.findStartDestination().id
            popUpTo(AppDestinations.SPLASH_ROUTE) { inclusive = true }
            // Avoid multiple copies of the same destination when re-selecting the same item
            launchSingleTop = true
            // Whether to restore state when re-selecting a previously selected item
            restoreState = false
        }
    }

    private fun navigateToAppDrawerRoute(route: String) {
        navController.navigate(route) {
            popUpTo(AppDestinations.MAIN_ROUTE) { saveState = true }
            launchSingleTop = true
            restoreState = true
        }
    }
}

private fun outputGraphInfo(route: String, navController: NavHostController) {
    LogContext.log.d(TAG, "  current: $route  previous=${navController.currentDestination?.route}")
    for ((i, dest) in navController.currentBackStack.value.withIndex()) {
        LogContext.log.d(TAG, "    Stack $i: ${dest.destination.route}")
    }
}