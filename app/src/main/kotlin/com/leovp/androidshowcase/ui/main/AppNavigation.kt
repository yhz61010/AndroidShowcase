package com.leovp.androidshowcase.ui.main

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.LibraryMusic
import androidx.compose.material.icons.outlined.MusicNote
import androidx.compose.material.icons.outlined.SpeakerNotes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import com.leovp.androidshowcase.R
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
    object Discovery : Screen("app_discovery", R.string.app_main_tab_discovery)
    object My : Screen("app_my", R.string.app_main_tab_my)
    object Community : Screen("app_community", R.string.app_main_tab_community)
}

enum class AppBottomNavigationItems(val screen: Screen, var icon: ImageVector) {
    DISCOVERY(Screen.Discovery, Icons.Outlined.LibraryMusic),
    MY(Screen.My, Icons.Outlined.MusicNote),
    COMMUNITY(Screen.Community, Icons.Outlined.SpeakerNotes)
}

/**
 * Models the navigation actions in the app.
 *
 * Attention:
 * This class can't be singleton. Otherwise, it will case the following exception
 * when you navigate to other screen after yous witch the device dark mode.
 * ```
 * java.lang.IllegalStateException:
 * no event down from INITIALIZED in component NavBackStackEntry(40f53e9f-981c-4e19-bcc0-69c85ed7ce77)
 * destination=Destination(0x88e673a4) route=app_main
 * ```
 */
class AppNavigationActions(private val navController: NavHostController) {
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

@Composable
fun getNavigationActions(navController: NavHostController): AppNavigationActions {
    return remember(navController) {
        AppNavigationActions(navController)
    }
}

private fun outputGraphInfo(route: String, navController: NavHostController) {
    LogContext.log.d(TAG, "  current: $route  previous=${navController.currentDestination?.route}")
    for ((i, dest) in navController.currentBackStack.value.withIndex()) {
        LogContext.log.d(TAG, "    Stack $i: ${dest.destination.route}")
    }
}