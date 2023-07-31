package com.leovp.androidshowcase.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.Mic
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
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
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.core.view.WindowCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.leovp.android.exts.toast
import com.leovp.androidshowcase.R
import com.leovp.androidshowcase.ui.tabs.community.CommunityScreen
import com.leovp.androidshowcase.ui.tabs.discovery.DiscoveryScreen
import com.leovp.androidshowcase.ui.tabs.my.MyScreen
import com.leovp.androidshowcase.ui.theme.AppTheme
import com.leovp.androidshowcase.ui.theme.discovery_top_section_end_color
import com.leovp.androidshowcase.ui.theme.discovery_top_section_middle2_color
import com.leovp.androidshowcase.ui.theme.discovery_top_section_middle3_color
import com.leovp.androidshowcase.ui.theme.discovery_top_section_start_color
import com.leovp.androidshowcase.util.toBadgeText
import com.leovp.androidshowcase.util.ui.SearchBar
import com.leovp.androidshowcase.util.ui.defaultLinearGradient
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

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    widthSize: WindowWidthSizeClass, modifier: Modifier = Modifier, navController: NavHostController
) {
    val statusBarHeight = WindowInsets.statusBars.asPaddingValues().calculateTopPadding().value
    val targetHeight = LocalDensity.current.run {
        // (56 + 150 + 2 * 8 + statusBarHeight).dp.toPx()
        (56 + 2 * 8 + statusBarHeight).dp.toPx()
    }

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

        val scrollState = rememberLazyListState()
        val firstVisibleItemIndex by remember { derivedStateOf { scrollState.firstVisibleItemIndex } }
        val firstVisibleItemScrollOffset by remember { derivedStateOf { scrollState.firstVisibleItemScrollOffset } }
        val scrolled = firstVisibleItemIndex != 0 || firstVisibleItemScrollOffset != 0

        Box(contentAlignment = Alignment.TopEnd) {
            Scaffold(modifier = modifier, topBar = {
                HomeTopAppBar(
                    modifier = modifier,
                    onNavigationClick = {
                        coroutineScope.launch { sizeAwareDrawerState.open() }
                    },
                    onActionClick = { context.toast("Recording is not yet implemented.") }
                ) {
                    when (pagerState.currentPage) {
                        AppBottomNavigationItems.DISCOVERY.ordinal -> {
                            SearchBar(
                                border = if (scrolled) {
                                    BorderStroke(
                                        width = 0.5.dp,
                                        color = Color.LightGray
                                    )
                                } else BorderStroke(
                                    width = 0.5.dp,
                                    brush = defaultLinearGradient
                                ),
                                backgroundBrush = if (scrolled) null else defaultLinearGradient,
                                modifier = Modifier
                                    .height(48.dp)
                                    .padding(vertical = 6.dp),
                                onClick = { context.toast("Click search bar.") },
                                onActionClick = { context.toast("Click scan button on search bar.") }
                            )
                        }
                    }
                }
            }, bottomBar = {
                NavigationBar {
                    AppBottomNavigationItems.values().forEachIndexed { index, bottomItemData ->
                        NavigationBarItem(
                            icon = {
                                when (index) {
                                    AppBottomNavigationItems.COMMUNITY.ordinal -> {
                                        val badgeNumber = 123.toBadgeText(999)
                                        val unreadContentDescription = stringResource(
                                            R.string.app_tab_community_unread,
                                            badgeNumber
                                        )
                                        BadgedBox(
                                            badge = {
                                                Badge {
                                                    Text(
                                                        badgeNumber,
                                                        modifier = Modifier.semantics {
                                                            contentDescription = unreadContentDescription
                                                        }
                                                    )
                                                }
                                            }) {
                                            Icon(bottomItemData.icon, stringResource(bottomItemData.screen.resId))
                                        }
                                    }

                                    else -> Icon(bottomItemData.icon, stringResource(bottomItemData.screen.resId))
                                }
                            },
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
                        AppBottomNavigationItems.DISCOVERY -> DiscoveryScreen(scrollState)
                        AppBottomNavigationItems.COMMUNITY -> CommunityScreen()
                        AppBottomNavigationItems.MY -> MyScreen()
                    }
                }
            } // end of Scaffold
            LinearGradientBox(scrollState, targetHeight)
        } // end of Box
    }
}

@Composable
fun LinearGradientBox(scrollState: LazyListState, targetHeight: Float) {
    val firstVisibleItemIndex by remember { derivedStateOf { scrollState.firstVisibleItemIndex } }
    val firstVisibleItemScrollOffset by remember { derivedStateOf { scrollState.firstVisibleItemScrollOffset } }
    val scrollPercent: Float = if (firstVisibleItemIndex > 0) 1f else firstVisibleItemScrollOffset / targetHeight

    Box(
        modifier = Modifier
            .testTag("linear-gradient-box")
            .fillMaxSize()
            .alpha(1 - scrollPercent)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.linearGradient(
                        listOf(
                            discovery_top_section_start_color,
                            discovery_top_section_middle2_color,
                            discovery_top_section_middle3_color,
                            discovery_top_section_end_color,
                        ),
                        start = Offset(Float.POSITIVE_INFINITY, 0f),
                        end = Offset(0f, Float.POSITIVE_INFINITY),
                        tileMode = TileMode.Clamp
                    )
                )
        ) {
            Spacer(modifier = Modifier.statusBarsPadding())
            Spacer(modifier = Modifier.height(64.dp))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTopAppBar(
    modifier: Modifier = Modifier,
    onNavigationClick: () -> Unit,
    onActionClick: () -> Unit,
    content: @Composable () -> Unit
) {
    val topBarHeight = 54.dp

    Column(
        modifier = modifier
            .fillMaxWidth()
            /*.background(color = Color.Cyan)*/,
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
        )
        Row(
            modifier = modifier.fillMaxWidth().heightIn(topBarHeight),
            verticalAlignment = Alignment.CenterVertically
        ) {
            ConstraintLayout(
                modifier = Modifier.wrapContentSize()
            ) {
                val (iconBtn, badge) = createRefs()
                IconButton(
                    modifier = Modifier.constrainAs(iconBtn) {}/*.background(color = Color.Yellow)*/,
                    onClick = onNavigationClick
                ) {
                    Icon(Icons.Filled.Menu, null)
                }
                val badgeNum = 23
                BadgedBox(
                    modifier = Modifier
                        .constrainAs(badge) {
                            top.linkTo(iconBtn.top, margin = 22.dp)
                            end.linkTo(iconBtn.end, margin = if (badgeNum < 100) 22.dp else 26.dp)
                        },
                    badge = {
                        Badge(
                            modifier = Modifier.border(
                                width = 2.dp,
                                color = MaterialTheme.colorScheme.onPrimary,
                                shape = CircleShape
                            )
                        ) {
                            Text(text = badgeNum.toBadgeText())
                        }
                    }) {
                }
            }
            Row(modifier = modifier.weight(1f)) {
                content()
            }
            IconButton(onClick = onActionClick) {
                Icon(Icons.Outlined.Mic, null)
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