package com.leovp.androidshowcase.presentation

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.statusBarsPadding
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
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.leovp.android.exts.toast
import com.leovp.androidshowcase.R
import com.leovp.androidshowcase.domain.model.UnreadModel
import com.leovp.androidshowcase.presentation.MainViewModel.MainUiEvent
import com.leovp.androidshowcase.presentation.MainViewModel.MainUiEvent.SearchEvent
import com.leovp.androidshowcase.presentation.MainViewModel.MainUiEvent.TopAppBarEvent
import com.leovp.androidshowcase.testdata.PreviewMainModule
import com.leovp.androidshowcase.ui.AppBottomNavigationItems
import com.leovp.androidshowcase.ui.AppDrawer
import com.leovp.androidshowcase.ui.AppNavigationActions
import com.leovp.androidshowcase.ui.DrawerDestinations
import com.leovp.androidshowcase.ui.Screen
import com.leovp.androidshowcase.ui.rememberNavigationActions
import com.leovp.community.presentation.CommunityScreen
import com.leovp.compose.composable.SearchBar
import com.leovp.compose.composable.defaultLinearGradient
import com.leovp.compose.composable.loading.ProgressIndicator
import com.leovp.compose.composable.rememberSizeAwareDrawerState
import com.leovp.compose.utils.previewInitLog
import com.leovp.compose.utils.toCounterBadgeText
import com.leovp.discovery.presentation.discovery.DiscoveryScreen
import com.leovp.discovery.presentation.discovery.DiscoveryViewModel
import com.leovp.discovery.presentation.discovery.DiscoveryViewModel.DiscoveryUiEvent
import com.leovp.discovery.testdata.PreviewDiscoveryModule
import com.leovp.feature.base.event.UiEventManager
import com.leovp.feature.base.event.composable.EventHandler
import com.leovp.log.LogContext
import com.leovp.log.base.d
import com.leovp.log.base.i
import com.leovp.mvvm.viewmodel.viewModelProviderFactoryOf
import com.leovp.my.presentation.MyScreen
import com.leovp.ui.theme.AppTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.net.URLEncoder

/**
 * Author: Michael Leo
 * Date: 2023/8/17 14:33
 */

private const val TAB_SWITCH_ANIM_DURATION = 300

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MainScreen(
    widthSize: WindowWidthSizeClass,
    navigationActions: AppNavigationActions,
    viewModel: MainViewModel = hiltViewModel<MainViewModel>(),
) {
    d(TAG) { "=> Enter MainScreen <=" }
    LaunchedEffect(Unit) {
        viewModel.onEnter()
    }

    val coroutineScope = rememberCoroutineScope()
    val isExpandedScreen by remember {
        mutableStateOf(widthSize == WindowWidthSizeClass.Expanded)
    }
    val sizeAwareDrawerState = rememberSizeAwareDrawerState(isExpandedScreen)

    ModalNavigationDrawer(
        drawerContent = {
            AppDrawer(
                currentRoute = DrawerDestinations.NO_ROUTE,
                onNavigateTo = { route -> navigationActions.navigate(route) },
                onCloseDrawer = {
                    coroutineScope.launch { sizeAwareDrawerState.close() }
                },
                modifier = Modifier.requiredWidth(300.dp),
            )
        },
        drawerState = sizeAwareDrawerState,
        // Only enable opening the drawer via gestures if the screen is not expanded
        gesturesEnabled = !isExpandedScreen,
    ) {
        val snackbarHostState = remember { SnackbarHostState() }
        EventHandler(
            events = viewModel.requireUiEvents,
            navController = null,
            snackbarHostState = snackbarHostState,
        )
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            val mainUiState by viewModel.uiStateFlow.collectAsStateWithLifecycle()
            var unreadList: List<UnreadModel>
            mainUiState.let {
                when (it) {
                    is MainViewModel.UiState.Content -> unreadList = it.unreadList

                    MainViewModel.UiState.Loading -> {
                        ProgressIndicator(
                            bgColor = MaterialTheme.colorScheme.background,
                            color = MaterialTheme.colorScheme.primary,
                            trackColor = MaterialTheme.colorScheme.primaryContainer,
                        )
                        return@Box
                    }
                }
            }
            MainContentScaffold(
                navigationActions = navigationActions,
                unreadList = unreadList,
                onEvent = { event ->
                    when (event) {
                        MainUiEvent.Refresh -> viewModel.onEnter()
                        is SearchEvent -> {
                            viewModel.handleTopAppBarContentEvent(
                                navigationActions,
                                event,
                            )
                        }

                        TopAppBarEvent.MenuClick -> {
                            coroutineScope.launch { sizeAwareDrawerState.open() }
                        }

                        is TopAppBarEvent -> {
                            viewModel.handleTopAppBarEvent(event)
                        }
                    }
                },
            )
        }
    }
}

@Composable
private fun MainContentScaffold(
    navigationActions: AppNavigationActions,
    unreadList: List<UnreadModel>,
    onEvent: (MainUiEvent) -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()
    val pagerScreenValues = AppBottomNavigationItems.entries.toTypedArray()
    val pagerState =
        rememberPagerState(
            initialPage = AppBottomNavigationItems.DISCOVERY.ordinal,
            initialPageOffsetFraction = 0f,
            pageCount = { pagerScreenValues.size },
        )
    Scaffold(
        topBar = {
            HomeTopAppBar(
                unread =
                    unreadList
                        .firstOrNull { it.key == UnreadModel.MESSAGE }
                        ?.value,
                pagerState = pagerState,
                onEvent = onEvent,
            ) {
                HomeTopAppBarContent(
                    // listState = listState,
                    pagerState = pagerState,
                    onEvent = onEvent,
                )
            }
        },
        bottomBar = { CustomBottomBar(pagerState, coroutineScope, unreadList) },
    ) { contentPadding ->
        MainScreenContent(
            navigationActions = navigationActions,
            pagerState = pagerState,
            pagerScreenValues = pagerScreenValues,
            onMainRefresh = { onEvent(MainUiEvent.Refresh) },
            modifier = Modifier.padding(contentPadding),
        )
    } // end of Scaffold
    // // The gradient box will significantly impact display performance.
    // LinearGradientBox(listState)
} // end of Box

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeTopAppBarContent(
    // listState: LazyListState,
    pagerState: PagerState,
    onEvent: (SearchEvent) -> Unit,
) {
    d(TAG) { "=> Enter HomeTopAppBarContent <=" }

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
                modifier =
                    Modifier
                        .height(48.dp)
                        .padding(vertical = 6.dp),
                searchIndicatorIcon = painterResource(id = R.drawable.app_search),
                actionIcon = painterResource(id = R.drawable.app_qr_code),
                onClick = { onEvent(SearchEvent.SearchClick) },
                onActionClick = { onEvent(SearchEvent.ScanClick) },
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun CustomBottomBar(
    pagerState: PagerState,
    coroutineScope: CoroutineScope,
    unreadList: List<UnreadModel> = emptyList(),
) {
    d(TAG) { "=> Enter CustomBottomBar <=" }
    NavigationBar {
        AppBottomNavigationItems.entries.forEachIndexed { index, bottomItemData ->
            val badgeNum =
                unreadList.firstOrNull { it.key == bottomItemData.screen.route }?.value
                    ?: 0
            NavigationBarItem(
                icon = {
                    if (badgeNum > 0) {
                        val badgeNumber = badgeNum.toCounterBadgeText(999)
                        val unreadContentDescription =
                            stringResource(
                                R.string.app_tab_unread_count,
                                badgeNumber,
                            )
                        BadgedBox(
                            badge = {
                                Badge {
                                    Text(
                                        text = badgeNumber,
                                        modifier =
                                            Modifier.semantics {
                                                contentDescription =
                                                    unreadContentDescription
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
                label = {
                    Text(
                        stringResource(bottomItemData.screen.nameResId),
                    )
                },
                // Here's the trick. The selected tab is based on HorizontalPager state.
                selected = index == pagerState.currentPage,
                onClick = {
                    LogContext.log.i(
                        TAG,
                        "Selected: ${bottomItemData.screen.route}",
                    )
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

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MainScreenContent(
    navigationActions: AppNavigationActions,
    pagerState: PagerState,
    pagerScreenValues: Array<AppBottomNavigationItems>,
    onMainRefresh: () -> Unit,
    modifier: Modifier = Modifier,
) {
    d(TAG) { "=> Enter MainScreenContent <=" }
    val ctx = LocalContext.current
    HorizontalPager(
        state = pagerState,
        modifier = modifier,
        key = { index -> pagerScreenValues[index].ordinal },
    ) { page ->
        when (pagerScreenValues[page]) {
            AppBottomNavigationItems.DISCOVERY ->
                DiscoveryScreen(
                    onRefresh = onMainRefresh,
                    onEvent = { event ->
                        when (event) {
                            is DiscoveryUiEvent.CarouselItemClick -> {
                                ctx.toast("Carousel recommend clickedItem: ${event.data}")
                            }

                            is DiscoveryUiEvent.PersonalItemClick -> {
                                val artist =
                                    URLEncoder.encode(
                                        event.data.getDefaultArtistName(),
                                        "UTF-8",
                                    )
                                val track = URLEncoder.encode(event.data.name, "UTF-8")
                                i(
                                    TAG,
                                ) { "Click [Personal Item] artist=$artist  track=$track" }
                                navigationActions.navigate(
                                    Screen.PlayerScreen.routeName,
                                    "${event.data.id}/$artist/$track",
                                )
                            }

                            is DiscoveryUiEvent.RecommendsItemClick -> {
                                ctx.toast("Everyday recommend clickedItem: ${event.data}")
                            }
                        }
                    },
                )

            AppBottomNavigationItems.MY -> MyScreen()
            AppBottomNavigationItems.COMMUNITY -> CommunityScreen()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun HomeTopAppBar(
    modifier: Modifier = Modifier,
    unread: Int? = 0,
    pagerState: PagerState,
    onEvent: (TopAppBarEvent) -> Unit,
    content: @Composable () -> Unit,
) {
    d(TAG) { "=> Enter HomeTopAppBar <=" }
    val topBarHeight = 54.dp

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
    ) {
        Spacer(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .statusBarsPadding(),
        )
        Row(
            modifier =
                modifier
                    .fillMaxWidth()
                    .heightIn(topBarHeight),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            val badgeNum = unread ?: 0
            if (badgeNum > 0) {
                Box {
                    HomeTopMenu(onClick = { onEvent(TopAppBarEvent.MenuClick) })
                    Badge(
                        modifier =
                            Modifier
                                .align(Alignment.BottomEnd)
                                .padding(6.dp)
                                .border(
                                    width = 2.dp,
                                    color = MaterialTheme.colorScheme.onPrimary,
                                    shape = CircleShape,
                                ),
                    ) {
                        Text(text = badgeNum.toCounterBadgeText())
                    }
                }
            } else {
                HomeTopMenu(onClick = { onEvent(TopAppBarEvent.MenuClick) })
            }
            Row(modifier = modifier.weight(1f)) {
                content()
            }
            if (pagerState.currentPage == AppBottomNavigationItems.DISCOVERY.ordinal) {
                IconButton(onClick = { onEvent(TopAppBarEvent.RecordingClick) }) {
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

    val mainViewModel: MainViewModel =
        viewModel(
            factory =
                viewModelProviderFactoryOf {
                    MainViewModel(
                        PreviewMainModule.previewMainUseCase,
                        UiEventManager(),
                    )
                },
        )

    viewModel<DiscoveryViewModel>(
        factory =
            viewModelProviderFactoryOf {
                DiscoveryViewModel(PreviewDiscoveryModule.previewDiscoveryListUseCase)
            },
    )

    AppTheme(dynamicColor = false) {
        val navController = rememberNavController()
        val navigationActions = rememberNavigationActions(navController = navController)

        MainScreen(
            widthSize = WindowWidthSizeClass.Compact,
            navigationActions = navigationActions,
            viewModel = mainViewModel,
        )
    }
}

@Preview(uiMode = UI_MODE_NIGHT_NO)
@Composable
fun PreviewMainScreenLoading() {
    previewInitLog()

    AppTheme(dynamicColor = false) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            ProgressIndicator(
                bgColor = MaterialTheme.colorScheme.background,
                color = MaterialTheme.colorScheme.primary,
                trackColor = MaterialTheme.colorScheme.primaryContainer,
            )
        }
    }
}
