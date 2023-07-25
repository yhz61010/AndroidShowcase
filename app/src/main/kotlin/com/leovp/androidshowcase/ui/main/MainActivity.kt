package com.leovp.androidshowcase.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.Mic
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.leovp.android.exts.toast
import com.leovp.androidshowcase.ui.tabs.home.HomeScreen
import com.leovp.androidshowcase.ui.tabs.interests.InterestsScreen
import com.leovp.androidshowcase.ui.tabs.my.MyScreen
import com.leovp.androidshowcase.ui.theme.AppTheme
import com.leovp.log.LogContext
import kotlinx.coroutines.launch

/**
 * Author: Michael Leo
 * Date: 2023/7/14 13:07
 */

private const val TAG = "MA"
private const val TAB_SWITCH_ANIM_DURATION = 300

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // This app draws behind the system bars, so we want to handle fitting system windows
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            val widthSizeClass = calculateWindowSizeClass(this).widthSizeClass
            ShowcaseApp(widthSizeClass)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun MainScreen(
    widthSize: WindowWidthSizeClass, modifier: Modifier = Modifier, navController: NavHostController
) {
    val navigationActions = getNavigationActions(navController = navController)

    val coroutineScope = rememberCoroutineScope()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route ?: DrawerDestinations.NO_ROUTE

    val isExpandedScreen = widthSize == WindowWidthSizeClass.Expanded
    val sizeAwareDrawerState = rememberSizeAwareDrawerState(isExpandedScreen)

    ModalNavigationDrawer(
        drawerContent = {
            AppDrawer(
                currentRoute = currentRoute,
                navigateTo = { route -> navigationActions.navigate(route) },
                closeDrawer = { coroutineScope.launch { sizeAwareDrawerState.close() } },
                modifier = Modifier.requiredWidth(300.dp)
            )
        }, drawerState = sizeAwareDrawerState,
        // Only enable opening the drawer via gestures if the screen is not expanded
        gesturesEnabled = !isExpandedScreen
    ) {
        val context = LocalContext.current

        var topBarTitleResId by remember { mutableStateOf(AppBottomNavigationItems.HOME.screen.resId) }

        val pagerState = rememberPagerState(initialPage = AppBottomNavigationItems.HOME.ordinal)
        val pagerScreenValues = AppBottomNavigationItems.values()

        Scaffold(modifier = modifier, topBar = {
            CenterAlignedTopAppBar(
                navigationIcon = {
                    IconButton(onClick = { coroutineScope.launch { sizeAwareDrawerState.open() } }) {
                        Icon(Icons.Filled.Menu, null)
                    }
                },
                title = { Text(stringResource(id = topBarTitleResId)) },
                actions = {
                    IconButton(onClick = { context.toast("Search is not yet implemented.") }) {
                        Icon(Icons.Outlined.Mic, null)
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        }, bottomBar = {
            NavigationBar {
                AppBottomNavigationItems.values().forEachIndexed { index, bottomItemData ->
                    NavigationBarItem(
                        icon = { Icon(bottomItemData.icon, stringResource(bottomItemData.screen.resId)) },
                        label = { Text(stringResource(bottomItemData.screen.resId)) },
                        // Here's the trick. The selected tab is based on HorizontalPager state.
                        selected = index == pagerState.currentPage,
                        onClick = {
                            LogContext.log.i(TAG, "Selected: ${bottomItemData.screen.route}")
                            coroutineScope.launch {
                                pagerState.animateScrollToPage(
                                    page = bottomItemData.ordinal,
                                    animationSpec = tween(TAB_SWITCH_ANIM_DURATION)
                                )
                            }
                        }
                    )
                }
            }
        }) { contentPadding ->
            val newModifier = modifier.padding(contentPadding)
            HorizontalPager(
                pageCount = pagerScreenValues.size,
                state = pagerState,
                modifier = newModifier
            ) { page ->
                topBarTitleResId = pagerScreenValues[pagerState.currentPage].screen.resId
                when (pagerScreenValues[page]) {
                    AppBottomNavigationItems.HOME -> HomeScreen()
                    AppBottomNavigationItems.INTERESTS -> InterestsScreen()
                    AppBottomNavigationItems.MY -> MyScreen()
                }
            }
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

@Preview
@Composable
fun PreviewMainScreen() {
    AppTheme(dynamicColor = false) {
        MainScreen(
            widthSize = WindowWidthSizeClass.Compact,
            navController = rememberNavController()
        )
    }
}