package com.leovp.androidshowcase.ui.main

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.leovp.androidshowcase.R
import com.leovp.log.LogContext

/**
 * Author: Michael Leo
 * Date: 2023/7/17 09:59
 */

private const val TAG = "Nav"

/**
 * Destinations used in the [ShowcaseApp].
 */
object AppDestinations {
    const val HOME_ROUTE = "home"
    const val INTERESTS_ROUTE = "interests"
    const val MY_ROUTE = "my"
}

sealed class Screen(val route: String, @StringRes val resId: Int) {
    object Home : Screen(AppDestinations.HOME_ROUTE, R.string.app_menu_home)
    object Interests : Screen(AppDestinations.INTERESTS_ROUTE, R.string.app_menu_interests)
    object My : Screen(AppDestinations.MY_ROUTE, R.string.app_menu_my)
}

enum class AppBottomNavigationItems(val screen: Screen, var icon: ImageVector) {
    HOME(Screen.Home, Icons.Outlined.Home),
    INTERESTS(Screen.Interests, Icons.Outlined.FavoriteBorder),
    MY(Screen.My, Icons.Outlined.Person)
}

/**
 * Models the navigation actions in the app.
 */
class AppNavigationActions(navController: NavHostController) {
    val navigateToHome: () -> Unit = {
        LogContext.log.i(TAG, "-> navigate to: ${AppDestinations.HOME_ROUTE}")
        navController.navigate(AppDestinations.HOME_ROUTE) {
            // Pop up to the start destination of the graph to
            // avoid building up a large stack of destinations
            // on the back stack as users select items
            popUpTo(navController.graph.findStartDestination().id) { saveState = true }
            // Avoid multiple copies of the same destination when re-selecting the same item
            launchSingleTop = true
            // Restore state when re-selecting a previously selected item
            restoreState = true
        }
    }

    val navigateToInterests: () -> Unit = {
        LogContext.log.i(TAG, "-> navigate to: ${AppDestinations.INTERESTS_ROUTE}")
        navController.navigate(AppDestinations.INTERESTS_ROUTE) {
            popUpTo(navController.graph.findStartDestination().id) { saveState = true }
            launchSingleTop = true
            restoreState = true
        }
    }

    val navigateToMy: () -> Unit = {
        LogContext.log.i(TAG, "-> navigate to: ${AppDestinations.MY_ROUTE}")
        navController.navigate(AppDestinations.MY_ROUTE) {
            popUpTo(navController.graph.findStartDestination().id) { saveState = true }
            launchSingleTop = true
            restoreState = true
        }
    }
}