package com.leovp.androidshowcase.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.Mic
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
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
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
import com.leovp.androidshowcase.util.ui.SearchBar
import com.leovp.androidshowcase.ui.tabs.community.CommunityScreen
import com.leovp.androidshowcase.ui.tabs.discovery.DiscoveryScreen
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

        val pagerState = rememberPagerState(initialPage = AppBottomNavigationItems.DISCOVERY.ordinal)
        val pagerScreenValues = AppBottomNavigationItems.values()

        Scaffold(modifier = modifier, topBar = {
            Column(
                modifier = modifier
                    .heightIn(56.dp)
                    .fillMaxWidth()
                    // .background(MaterialTheme.colorScheme.primaryContainer),
            ) {
                Spacer(modifier = Modifier.statusBarsPadding())
                Row(
                    modifier = modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = { coroutineScope.launch { sizeAwareDrawerState.open() } }) {
                        Icon(Icons.Filled.Menu, null)
                    }
                    when (pagerState.currentPage) {
                        AppBottomNavigationItems.DISCOVERY.ordinal -> {
                            SearchBar(
                                modifier = modifier.weight(1f),
                                onClick = {
                                    context.toast("Click search bar.")
                                },
                                onActionClick = {
                                    context.toast("Click scan button on search bar.")
                                }
                            )
                        }
                    }
                    IconButton(onClick = { context.toast("Recording is not yet implemented.") }) {
                        Icon(Icons.Outlined.Mic, null)
                    }
                }
            }

            // TopAppBar(
            //     navigationIcon = {
            //         IconButton(onClick = { coroutineScope.launch { sizeAwareDrawerState.open() } }) {
            //             Icon(Icons.Filled.Menu, null)
            //         }
            //     },
            //     title = {
            //         when (pagerState.currentPage) {
            //             AppBottomNavigationItems.DISCOVERY.ordinal -> {
            //                 SearchBar(
            //                     query = TextFieldValue(""),
            //                     onQueryChange = { },
            //                     searchFocused = false,
            //                     onSearchFocusChange = { },
            //                     onClearQuery = { },
            //                     searching = false
            //                 )
            //             }
            //         }
            //     },
            //     actions = {
            //         IconButton(onClick = { context.toast("Recording is not yet implemented.") }) {
            //             Icon(Icons.Outlined.Mic, null)
            //         }
            //     },
            //     colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            //         containerColor = MaterialTheme.colorScheme.primaryContainer
            //     )
            // )
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
                when (pagerScreenValues[page]) {
                    AppBottomNavigationItems.DISCOVERY -> DiscoveryScreen()
                    AppBottomNavigationItems.COMMUNITY -> CommunityScreen()
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