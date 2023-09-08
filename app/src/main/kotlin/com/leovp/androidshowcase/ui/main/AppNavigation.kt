package com.leovp.androidshowcase.ui.main

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
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
// Drawer destinations
// --------------------
object DrawerDestinations {
    const val NO_ROUTE = "drawer_no"
}

/**
 * Define all the screens used in this application.
 */
sealed class Screen(
    val route: String,
    @StringRes val nameResId: Int = 0,
    val iconVector: ImageVector? = null,
    @DrawableRes val iconResId: Int = 0,
) {
    inline fun <reified T : Any> getIcon(): T {
        return (iconVector ?: iconResId) as T
    }

    data object Splash : Screen("app_splash")
    data object Main : Screen("app_main")

    // Home tab screens
    data object Discovery : Screen(
        route = "app_discovery",
        nameResId = R.string.app_main_tab_discovery,
        iconResId = R.drawable.app_library_music,
    )

    data object My : Screen(
        route = "app_my",
        nameResId = R.string.app_main_tab_my,
        iconResId = R.drawable.app_music_note
    )

    data object Community : Screen(
        route = "app_community",
        nameResId = R.string.app_main_tab_community,
        iconResId = R.drawable.app_speaker_notes
    )

    // Drawer item screens
    data object MemberCenterScreen : Screen(
        route = "drawer_member_center",
        nameResId = R.string.app_drawer_member_center,
        iconResId = R.drawable.app_credit_card
    )

    data object MessageScreen :
        Screen("drawer_messages", R.string.app_drawer_message_label, iconResId = R.drawable.app_mail)

    data object SettingScreen :
        Screen("drawer_setting", R.string.app_drawer_settings_label, iconResId = R.drawable.app_settings)
}

enum class AppBottomNavigationItems(val screen: Screen) {
    DISCOVERY(Screen.Discovery),

    MY(Screen.My),

    COMMUNITY(Screen.Community)
}

/**
 * Models the navigation actions in the app.
 *
 * DO NOT use this class directly, use [rememberNavigationActions] instead.
 *
 * Attention:
 * This class can't be singleton. Otherwise, it will cause the following exception
 * when you navigate to other screen after you switch the device to dark mode.
 * ```
 * java.lang.IllegalStateException:
 * no event down from INITIALIZED in component NavBackStackEntry(40f53e9f-981c-4e19-bcc0-69c85ed7ce77)
 * destination=Destination(0x88e673a4) route=app_main
 * ```
 */
class AppNavigationActions(private val navController: NavHostController) {
    @Suppress("unused")
    val currentRoute: String? get() = navController.currentDestination?.route

    fun upPress() {
        navController.navigateUp()
    }

    fun navigate(route: String) {
        LogContext.log.i(TAG, "-> navigate to: $route")
        outputGraphInfo(route, navController)
        return when (route) {
            Screen.Main.route -> navController.navigateToMain()

            Screen.MemberCenterScreen.route, Screen.MessageScreen.route, Screen.SettingScreen.route -> navController.navigateSingleTopTo(
                route
            )

            else -> error("Illegal route: $route")
        }
    }
}

fun NavHostController.navigateToMain() = this.navigate(Screen.Main.route) {
    // Pop up to the start destination of the graph to
    // avoid building up a large stack of destinations
    // on the back stack as users select items
    //
    // navController.graph.findStartDestination().id
    popUpTo(Screen.Splash.route) { inclusive = true }
    // Avoid multiple copies of the same destination when re-selecting the same item
    launchSingleTop = true
    // Whether to restore state when re-selecting a previously selected item
    restoreState = false
}

fun NavHostController.navigateSingleTopTo(route: String) = this.navigate(route) {
    // Pop up to the start destination of the graph to
    // avoid building up a large stack of destinations
    // on the back stack as users select items
    popUpTo(Screen.Main.route) { saveState = true }
    // Avoid multiple copies of the same destination when re-selecting the same item
    launchSingleTop = true
    // Whether to restore state when re-selecting a previously selected item
    restoreState = true
}

@Composable
fun rememberNavigationActions(navController: NavHostController): AppNavigationActions {
    return remember { AppNavigationActions(navController) }
}

private fun outputGraphInfo(route: String, navController: NavHostController) {
    LogContext.log.d(TAG, "  current: $route  previous=${navController.currentDestination?.route}")
    for ((i, dest) in navController.currentBackStack.value.withIndex()) {
        LogContext.log.d(TAG, "    Stack $i: ${dest.destination.route}")
    }
}