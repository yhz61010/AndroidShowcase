package com.leovp.androidshowcase.ui.main

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.leovp.androidshowcase.ui.home.HomeScreen
import com.leovp.log.LogContext
import kotlinx.coroutines.launch

/**
 * Author: Michael Leo
 * Date: 2023/7/17 16:35
 */

private const val TAG = "AppHome"

@Composable
fun AppHome(
    widthSize: WindowWidthSizeClass,
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    val navigationActions = remember(navController) {
        AppNavigationActions(navController)
    }

    val coroutineScope = rememberCoroutineScope()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route ?: AppDestinations.HOME_ROUTE

    val isExpandedScreen = widthSize == WindowWidthSizeClass.Expanded
    val sizeAwareDrawerState = rememberSizeAwareDrawerState(isExpandedScreen)

    ModalNavigationDrawer(
        drawerContent = {
            AppDrawer(
                currentRoute = currentRoute,
                navigateToHome = navigationActions.navigateToHome,
                navigateToInterests = navigationActions.navigateToInterests,
                navigateToMy = navigationActions.navigateToMy,
                closeDrawer = { coroutineScope.launch { sizeAwareDrawerState.close() } },
                modifier = Modifier.requiredWidth(300.dp)
            )
        },
        drawerState = sizeAwareDrawerState,
        // Only enable opening the drawer via gestures if the screen is not expanded
        gesturesEnabled = !isExpandedScreen
    ) {
        // modifier.statusBarsPadding()
        var currentSelect by remember { mutableStateOf(AppBottomNavigationItems.HOME.screen.route) }

        Scaffold(modifier = modifier, bottomBar = {
            NavigationBar {
                // val navBackStackEntry by navController.currentBackStackEntryAsState()
                // val currentDestination = navBackStackEntry?.destination?.route ?: AppBottomNavigationItems.HOME.screen.route
                // LogContext.log.d(TAG, "currentDestination=$currentDestination")
                AppBottomNavigationItems.values().forEach { bottomItemData ->
                    NavigationBarItem(icon = { Icon(bottomItemData.icon, stringResource(bottomItemData.screen.resId)) },
                        label = { Text(stringResource(bottomItemData.screen.resId)) },
                        selected = currentSelect == bottomItemData.screen.route,
                        onClick = {
                            currentSelect = bottomItemData.screen.route
                            LogContext.log.i(TAG, "Selected: $currentSelect")
                            // val navigationInvoke = when (currentSelect) {
                            //     ShowcaseDestinations.HOME_ROUTE -> navigationActions.navigateToHome
                            //     ShowcaseDestinations.INTERESTS_ROUTE -> navigationActions.navigateToInterests
                            //     ShowcaseDestinations.MY_ROUTE -> navigationActions.navigateToMy
                            //     else -> null
                            // }
                            // navigationInvoke?.invoke()
                        })
                }
            }
        }) { contentPadding ->
            HomeScreen(modifier = modifier.padding(contentPadding))
        }
    }
}


/**
 * Determine the drawer state to pass to the modal drawer.
 */
@Composable
private fun rememberSizeAwareDrawerState(isExpandedScreen: Boolean): DrawerState {
    val drawerState = rememberDrawerState(DrawerValue.Closed)

    return if (!isExpandedScreen) {
        // If we want to allow showing the drawer, we use a real, remembered drawer
        // state defined above
        drawerState
    } else {
        // If we don't want to allow the drawer to be shown, we provide a drawer state
        // that is locked closed. This is intentionally not remembered, because we
        // don't want to keep track of any changes and always keep it closed
        DrawerState(DrawerValue.Closed)
    }
}

