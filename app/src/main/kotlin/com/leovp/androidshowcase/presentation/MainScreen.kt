package com.leovp.androidshowcase.presentation

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
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
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
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.leovp.android.exts.toast
import com.leovp.androidshowcase.R
import com.leovp.androidshowcase.domain.model.UnreadModel
import com.leovp.androidshowcase.testdata.PreviewMainModule
import com.leovp.androidshowcase.ui.AppBottomNavigationItems
import com.leovp.androidshowcase.ui.AppDrawer
import com.leovp.androidshowcase.ui.DrawerDestinations
import com.leovp.androidshowcase.ui.Screen
import com.leovp.androidshowcase.ui.theme.AppTheme
import com.leovp.androidshowcase.ui.theme.discovery_top_section_end_color
import com.leovp.androidshowcase.ui.theme.discovery_top_section_middle2_color
import com.leovp.androidshowcase.ui.theme.discovery_top_section_middle3_color
import com.leovp.androidshowcase.ui.theme.discovery_top_section_start_color
import com.leovp.feature_community.presentation.CommunityScreen
import com.leovp.feature_discovery.presentation.DiscoveryScreen
import com.leovp.feature_discovery.presentation.DiscoveryUiState
import com.leovp.feature_discovery.presentation.DiscoveryViewModel
import com.leovp.feature_discovery.testdata.PreviewDiscoveryModule
import com.leovp.feature_my.presentation.MyScreen
import com.leovp.log.LogContext
import com.leovp.module.common.log.d
import com.leovp.module.common.presentation.compose.composable.SearchBar
import com.leovp.module.common.presentation.compose.composable.defaultLinearGradient
import com.leovp.module.common.presentation.compose.composable.rememberSizeAwareDrawerState
import com.leovp.module.common.presentation.viewmodel.viewModelProviderFactoryOf
import com.leovp.module.common.utils.previewInitLog
import com.leovp.module.common.utils.toBadgeText
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * Author: Michael Leo
 * Date: 2023/8/17 14:33
 */

private const val TAG = "MS"
private const val TAB_SWITCH_ANIM_DURATION = 300

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    widthSize: WindowWidthSizeClass,
    mainUiState: MainUiState,
    discoveryUiState: DiscoveryUiState,
    onNavigationToDrawerItem: (drawerItemRoute: String) -> Unit,
    onSearchBarClick: () -> Unit,
    onDiscoveryRefresh: () -> Unit,
) {
    d(TAG) { "=> Enter MainScreen <=" }
    val context = LocalContext.current

    val coroutineScope = rememberCoroutineScope()
    val isExpandedScreen = widthSize == WindowWidthSizeClass.Expanded
    val sizeAwareDrawerState = rememberSizeAwareDrawerState(isExpandedScreen)

    val pagerScreenValues = AppBottomNavigationItems.entries.toTypedArray()
    val pagerState = rememberPagerState(
        initialPage = AppBottomNavigationItems.DISCOVERY.ordinal,
        initialPageOffsetFraction = 0f,
        pageCount = { pagerScreenValues.size },
    )

    val listState = rememberLazyListState()
    ModalNavigationDrawer(
        drawerContent = {
            AppDrawer(
                currentRoute = DrawerDestinations.NO_ROUTE,
                onNavigateTo = { route -> onNavigationToDrawerItem(route) },
                onCloseDrawer = { coroutineScope.launch { sizeAwareDrawerState.close() } },
                modifier = Modifier.requiredWidth(300.dp)
            )
        }, drawerState = sizeAwareDrawerState,
        // Only enable opening the drawer via gestures if the screen is not expanded
        gesturesEnabled = !isExpandedScreen
    ) {
        Box(contentAlignment = Alignment.TopEnd) {
            Scaffold(modifier = modifier, topBar = {
                HomeTopAppBar(
                    modifier = modifier,
                    unread = mainUiState.unreadList.firstOrNull { it.key == UnreadModel.MESSAGE }?.value,
                    pagerState = pagerState,
                    onNavigationClick = { coroutineScope.launch { sizeAwareDrawerState.open() } },
                    onActionClick = {
                        context.toast("Recording is not yet implemented.")
                    },
                ) {
                    HomeTopAppBarContent(
                        listState = listState,
                        pagerState = pagerState,
                        onClick = onSearchBarClick,
                    )
                }
            }, bottomBar = {
                CustomBottomBar(pagerState, coroutineScope, mainUiState.unreadList)
            }) { contentPadding ->
                val newModifier = modifier.padding(contentPadding)
                MainScreenContent(
                    modifier = newModifier,
                    onDiscoveryRefresh = onDiscoveryRefresh,
                    pagerState = pagerState,
                    listState = listState,
                    pagerScreenValues = pagerScreenValues,
                    discoveryUiState = discoveryUiState,
                )
            } // end of Scaffold

            LinearGradientBox(listState)
        } // end of Box
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeTopAppBarContent(
    listState: LazyListState,
    pagerState: PagerState,
    onClick: () -> Unit = {},
) {
    d(TAG) { "=> Enter HomeTopAppBarContent <=" }
    val context = LocalContext.current

    // val firstVisibleItemIndex by remember { derivedStateOf { listState.firstVisibleItemIndex } }
    // val firstVisibleItemScrollOffset by remember {
    //     derivedStateOf { listState.firstVisibleItemScrollOffset }
    // }
    // val scrolled = firstVisibleItemIndex != 0 || firstVisibleItemScrollOffset != 0

    when (pagerState.currentPage) {
        AppBottomNavigationItems.DISCOVERY.ordinal -> {
            SearchBar(
                searchText = "Wellerman Nathan Evans",
                border = BorderStroke(width = 0.5.dp, brush = defaultLinearGradient),
                backgroundBrush = defaultLinearGradient,
                modifier = Modifier
                    .height(48.dp)
                    .padding(vertical = 6.dp),
                searchIndicatorIcon = painterResource(id = R.drawable.app_search),
                actionIcon = painterResource(id = R.drawable.app_qr_code),
                onClick = onClick,
                onActionClick = { context.toast("Click scan button on search bar.") },
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun CustomBottomBar(
    pagerState: PagerState,
    coroutineScope: CoroutineScope,
    unreadList: List<UnreadModel> = emptyList()
) {
    d(TAG) { "=> Enter CustomBottomBar <=" }
    NavigationBar {
        AppBottomNavigationItems.entries.forEachIndexed { index, bottomItemData ->
            val badgeNum =
                unreadList.firstOrNull { it.key == bottomItemData.screen.route }?.value ?: 0
            NavigationBarItem(
                icon = {
                    if (badgeNum > 0) {
                        val badgeNumber = badgeNum.toBadgeText(999)
                        val unreadContentDescription = stringResource(
                            R.string.app_tab_unread_count, badgeNumber
                        )
                        BadgedBox(
                            badge = {
                                Badge {
                                    Text(
                                        text = badgeNumber,
                                        modifier = Modifier.semantics {
                                            contentDescription = unreadContentDescription
                                        },
                                    )
                                }
                            },
                        ) {
                            TabIcon(bottomItemData.screen)
                        }
                    } else {
                        TabIcon(bottomItemData.screen)
                    }
                },
                label = { Text(stringResource(bottomItemData.screen.nameResId)) },
                // Here's the trick. The selected tab is based on HorizontalPager state.
                selected = index == pagerState.currentPage,
                onClick = {
                    LogContext.log.i(TAG, "Selected: ${bottomItemData.screen.route}")
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(
                            page = bottomItemData.ordinal,
                            animationSpec = tween(TAB_SWITCH_ANIM_DURATION),
                        )
                    }
                },
            ) // end NavigationBarItem
        } // end AppBottomNavigationItems
    } // end NavigationBar
}

@Composable
private fun TabIcon(screen: Screen) {
    Icon(
        painter = painterResource(id = screen.iconResId),
        contentDescription = stringResource(screen.nameResId),
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MainScreenContent(
    modifier: Modifier = Modifier,
    pagerState: PagerState,
    listState: LazyListState,
    pagerScreenValues: Array<AppBottomNavigationItems>,
    discoveryUiState: DiscoveryUiState,
    onDiscoveryRefresh: () -> Unit,
) {
    d(TAG) { "=> Enter MainScreenContent <=" }
    HorizontalPager(
        state = pagerState,
        modifier = modifier,
        key = { index -> pagerScreenValues[index].ordinal },
    ) { page ->
        when (pagerScreenValues[page]) {
            AppBottomNavigationItems.DISCOVERY -> DiscoveryScreen(
                listState = listState,
                uiState = discoveryUiState,
                onRefresh = onDiscoveryRefresh,
            )

            AppBottomNavigationItems.MY -> MyScreen(/*onRefresh*/)
            AppBottomNavigationItems.COMMUNITY -> CommunityScreen(/*onRefresh*/)
        }
    }
}

@Composable
fun LinearGradientBox(scrollState: LazyListState) {
    d(TAG) { "=> Enter LinearGradientBox <=" }
    val statusBarHeight = WindowInsets.statusBars.asPaddingValues().calculateTopPadding().value
    val targetHeight = LocalDensity.current.run {
        // (56 + 150 + 2 * 8 + statusBarHeight).dp.toPx()
        (56 + 2 * 8 + statusBarHeight).dp.toPx()
    }

    val firstVisibleItemIndex by remember { derivedStateOf { scrollState.firstVisibleItemIndex } }
    val firstVisibleItemScrollOffset by remember { derivedStateOf { scrollState.firstVisibleItemScrollOffset } }
    val scrollPercent: Float =
        if (firstVisibleItemIndex > 0) 1f else firstVisibleItemScrollOffset / targetHeight

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

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun HomeTopAppBar(
    modifier: Modifier = Modifier,
    unread: Int? = 0,
    pagerState: PagerState,
    onNavigationClick: () -> Unit,
    onActionClick: () -> Unit,
    content: @Composable () -> Unit,
) {
    d(TAG) { "=> Enter HomeTopAppBar <=" }
    val topBarHeight = 54.dp

    Column(
        modifier = modifier.fillMaxWidth()/*.background(color = Color.Cyan)*/,
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
        )
        Row(
            modifier = modifier
                .fillMaxWidth()
                .heightIn(topBarHeight),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier.wrapContentSize(),
                contentAlignment = Alignment.TopEnd,
            ) {
                IconButton(
                    // .background(color = Color.Yellow),
                    onClick = onNavigationClick
                ) { Icon(painterResource(R.drawable.app_menu), null) }
                val badgeNum = unread ?: 0
                if (badgeNum > 0) {
                    BadgedBox(
                        modifier = Modifier.padding(
                            0.dp, 22.dp, if (badgeNum < 10) 20.dp else 24.dp, 0.dp
                        ),
                        badge = {
                            Badge(
                                modifier = Modifier.border(
                                    width = 2.dp,
                                    color = MaterialTheme.colorScheme.onPrimary,
                                    shape = CircleShape,
                                )
                            ) {
                                Text(text = badgeNum.toBadgeText())
                            }
                        },
                    ) {}
                }
            }
            Row(modifier = modifier.weight(1f)) {
                content()
            }
            if (pagerState.currentPage == AppBottomNavigationItems.DISCOVERY.ordinal) {
                IconButton(onClick = onActionClick) {
                    Icon(
                        painter = painterResource(id = R.drawable.app_mic),
                        contentDescription = null,
                    )
                }
            }
        } // end row
    } // end Column
}

@Preview
@Composable
fun PreviewMainScreen() {
    previewInitLog()

    val mainViewModel: MainViewModel = viewModel(
        factory = viewModelProviderFactoryOf {
            MainViewModel(PreviewMainModule.previewMainUseCase)
        },
    )

    val discoveryViewModel: DiscoveryViewModel = viewModel(
        factory = viewModelProviderFactoryOf {
            DiscoveryViewModel(PreviewDiscoveryModule.previewDiscoveryListUseCase)
        },
    )

    AppTheme(dynamicColor = false) {
        MainScreen(
            widthSize = WindowWidthSizeClass.Compact,
            onNavigationToDrawerItem = {},
            mainUiState = mainViewModel.uiState.value,
            discoveryUiState = discoveryViewModel.uiState.value,
            onSearchBarClick = {},
            onDiscoveryRefresh = {},
        )
    }
}