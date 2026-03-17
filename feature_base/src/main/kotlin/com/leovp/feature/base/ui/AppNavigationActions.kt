@file:Suppress("unused")

package com.leovp.feature.base.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import com.leovp.compose.composable.nav.AppNavigation
import com.leovp.compose.utils.navigateSingleTopTo
import com.leovp.compose.utils.navigateTo
import com.leovp.mvvm.event.base.UiEvent

/**
 * Author: Michael Leo
 * Date: 2023/7/17 09:59
 */

private const val TAG = "Nav"

// --------------------
// Drawer destinations
// --------------------
object DrawerDestinations {
    const val NO_ROUTE = "drawer_dst"
}

/**
 * Models the navigation actions in the app.
 *
 * DO NOT use this class directly, use [com.leovp.feature.base.ui.nav.rememberNavigationActions] instead.
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
class AppNavigationActions(
    private val navController: NavHostController,
) : AppNavigation(navController) {

    override fun navigate(
        route: String,
        arguments: String?,
        extras: UiEvent.NavExtras?,
    ) {
        super.navigate(route, arguments, extras)
        return when (route) {
            Screen.Main.route -> navController.navigateToMain()

            Screen.MemberCenter.route,
            Screen.Search.route,
            Screen.Player.routeName,
            Screen.Message.route,
            Screen.Setting.route,
            Screen.Comment.routeName,
                ->
                navController.navigateSingleTopTo(
                    route,
                    arguments,
                )

            else -> error("Illegal route: $route")
        }
    }
}

private fun NavHostController.navigateToMain() =
    navigate(Screen.Main.route) {
        // Pop up to the start destination of the graph to
        // avoid building up a large stack of destinations
        // on the back stack as users select items
        //
        // navController.graph.findStartDestination().id

        // Clear the back stack up to the given route.
        // `Screen.Splash.route`, meaning everything back to the `Splash` screen will be popped.
        // `inclusive = true` ensures that `Splash` itself is also removed.
        // Effect: Once the user reaches `Main`,
        // pressing the back button won't return them to `Splash` — it will exit the app instead.
        popUpTo(Screen.Splash.route) { inclusive = true }
        // Avoid multiple copies of the same destination when re-selecting the same item
        launchSingleTop = true
        // Whether to restore state when re-selecting a previously selected item
        restoreState = false
    }

fun NavHostController.navigateSingleTopPopUpToMain(
    route: String,
    arguments: String? = null,
) {
    navigateTo(route, arguments) {
        // Pop up to the start destination of the graph to
        // avoid building up a large stack of destinations
        // on the back stack as users select items
        //
        // navController.graph.findStartDestination().id

        // Clear the back stack up to the given route.
        // `route`, meaning everything back to the `route` screen will be popped.
        // `inclusive = false`(default value) ensures that `Main` itself is NOT removed.
        // Effect: Once the user reaches `route`,
        // pressing the back button will return to `Main` screen.
        popUpTo(Screen.Main.route) { saveState = true }
        // Avoid multiple copies of the same destination when re-selecting the same item
        launchSingleTop = true
        // Whether to restore state when re-selecting a previously selected item
        restoreState = true
    }
}
