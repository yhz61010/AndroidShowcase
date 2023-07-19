package com.leovp.androidshowcase.ui.main

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import com.leovp.androidshowcase.R

/**
 * Author: Michael Leo
 * Date: 2023/7/17 09:59
 */

private const val TAG = "Nav"

object AppDestinations {
    const val SPLASH_ROUTE = "app_splash"
    const val MAIN_ROUTE = "app_main"

    const val HOME_ROUTE = "app_home"
    const val INTERESTS_ROUTE = "app_interests"
    const val MY_ROUTE = "app_my"
}

object DrawerDestinations {
    const val NO_ROUTE = "drawer_no"
    const val PROFILE_ROUTE = "drawer_profile"
    const val VIP_ROUTE = "drawer_vip"
    const val MESSAGES = "drawer_messages"
    const val SETTING_ROUTE = "drawer_setting"
    const val EXIT_ROUTE = "drawer_exit"
}

sealed class Screen(val route: String, @StringRes val resId: Int) {
    object Home : Screen(AppDestinations.HOME_ROUTE, R.string.app_main_tab_home)
    object Interests : Screen(AppDestinations.INTERESTS_ROUTE, R.string.app_main_tab_interests)
    object My : Screen(AppDestinations.MY_ROUTE, R.string.app_main_tab_my)
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
    val navigationToMain: () -> Unit = {
        navController.navigate(AppDestinations.MAIN_ROUTE) {
            // Pop up to the start destination of the graph to
            // avoid building up a large stack of destinations
            // on the back stack as users select items
            popUpTo(AppDestinations.SPLASH_ROUTE) { inclusive = true }
            // Avoid multiple copies of the same destination when re-selecting the same item
            launchSingleTop = true
        }
    }

    val navigateToHome: () -> Unit = {
        navController.navigate(AppDestinations.HOME_ROUTE) {
            // Pop up to the start destination of the graph to
            // avoid building up a large stack of destinations
            // on the back stack as users select items
            popUpTo(AppDestinations.MAIN_ROUTE) { saveState = true }
            // Avoid multiple copies of the same destination when re-selecting the same item
            launchSingleTop = true
            // Restore state when re-selecting a previously selected item
            restoreState = true
        }
    }

    val navigateToInterests: () -> Unit = {
        navController.navigate(AppDestinations.INTERESTS_ROUTE) {
            popUpTo(AppDestinations.MAIN_ROUTE) { saveState = true }
            launchSingleTop = true
            restoreState = true
        }
    }

    val navigateToMy: () -> Unit = {
        navController.navigate(AppDestinations.MY_ROUTE) {
            popUpTo(AppDestinations.MAIN_ROUTE) { saveState = true }
            launchSingleTop = true
            restoreState = true
        }
    }

    val navigateToDrawerVip: () -> Unit = {
        navController.navigate(DrawerDestinations.VIP_ROUTE) {
            popUpTo(AppDestinations.MAIN_ROUTE) { saveState = true }
            launchSingleTop = true
            restoreState = true
        }
    }

    val navigateToDrawerMessage: () -> Unit = {
        navController.navigate(DrawerDestinations.MESSAGES) {
            popUpTo(AppDestinations.MAIN_ROUTE) { saveState = true }
            launchSingleTop = true
            restoreState = true
        }
    }

    val navigateToDrawerSetting: () -> Unit = {
        navController.navigate(DrawerDestinations.SETTING_ROUTE) {
            popUpTo(AppDestinations.MAIN_ROUTE) { saveState = true }
            launchSingleTop = true
            restoreState = true
        }
    }

    val navigateToDrawerExit: () -> Unit = {
        navController.navigate(DrawerDestinations.EXIT_ROUTE) {
            popUpTo(AppDestinations.MAIN_ROUTE) { saveState = true }
            launchSingleTop = true
            restoreState = true
        }
    }
}