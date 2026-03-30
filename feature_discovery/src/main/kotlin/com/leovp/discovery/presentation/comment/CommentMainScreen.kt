package com.leovp.discovery.presentation.comment

import android.content.res.Configuration
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.leovp.compose.composable.nav.AppNavigation
import com.leovp.compose.ui.LocalNavigationActions
import com.leovp.discovery.presentation.comment.composable.CommentItem
import com.leovp.discovery.presentation.comment.composable.FilterTabs
import com.leovp.discovery.presentation.comment.composable.SongInfoHeader
import com.leovp.discovery.testdata.local.datasource.LocalCommentData
import com.leovp.feature.base.event.composable.CustomEventHandler
import com.leovp.feature.base.ui.CommentTabs
import com.leovp.feature.base.ui.PreviewWrapper
import com.leovp.feature.base.ui.Screen
import com.leovp.mvvm.event.base.UiEventManager
import com.leovp.mvvm.viewmodel.viewModelProviderFactoryOf
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import com.leovp.feature.base.R as BaseR

/**
 * Author: Michael Leo
 * Date: 2025/8/26 13:14
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommentMainScreen(viewModel: CommentViewModel = hiltViewModel<CommentViewModel>()) {
    // val context = LocalContext.current
    val navController = LocalNavigationActions.current
    CustomEventHandler(
        events = viewModel.requireUiEvents,
        navController = navController,
    )

    val coroutineScope = rememberCoroutineScope()
    val pagerScreenValues = CommentTabs.entries.toTypedArray()
    val pagerState =
        rememberPagerState(
            initialPage = CommentTabs.COMMENT.ordinal,
            initialPageOffsetFraction = 0f,
            pageCount = { pagerScreenValues.size },
        )

    val topAppBarState = rememberTopAppBarState()
    val scrollBehavior =
        TopAppBarDefaults.enterAlwaysScrollBehavior(
            topAppBarState,
        )
    Scaffold(
        // contentWindowInsets = WindowInsets(0.dp, 0.dp, 0.dp, 0.dp),
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CommentTopBar(
                pagerState = pagerState,
                pagerScreenValues = pagerScreenValues,
                coroutineScope = coroutineScope,
                navController = navController,
                scrollBehavior = scrollBehavior,
            )
        },
    ) { contentPadding ->
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.padding(contentPadding),
        ) { page ->
            when (page) {
                0 -> CommentTabContent(viewModel.songInfo)
                1 -> NotesTabContent()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CommentTopBar(
    pagerState: PagerState,
    pagerScreenValues: Array<CommentTabs>,
    coroutineScope: CoroutineScope,
    navController: AppNavigation,
    scrollBehavior: androidx.compose.material3.TopAppBarScrollBehavior,
) {
    CenterAlignedTopAppBar(
        // colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
        //     containerColor = Color.Cyan
        // ),
        // WindowInsets.waterfall // WindowInsets.displayCutout // or all 0.dp
        // windowInsets = WindowInsets(0.dp, 0.dp, 0.dp, 0.dp),
        title = {
            PrimaryTabRow(
                selectedTabIndex = pagerState.currentPage,
                modifier = Modifier.fillMaxWidth(),
                // containerColor = Color.White,
                contentColor = Color.Black,
                divider = {},
                indicator = {
                    TabRowDefaults.PrimaryIndicator(
                        modifier =
                            Modifier.tabIndicatorOffset(
                                pagerState.currentPage,
                            ),
                        height = 3.dp,
                        color = MaterialTheme.colorScheme.primary,
                        shape = RoundedCornerShape(2.dp),
                    )
                },
            ) {
                pagerScreenValues.forEachIndexed { index, item ->
                    Tab(
                        text = {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text =
                                        stringResource(
                                            pagerScreenValues[index].nameResId,
                                        ),
                                    color = MaterialTheme.colorScheme.onSurface,
                                )
                            }
                        },
                        selected = pagerState.currentPage == index,
                        onClick = {
                            // Will be handled by pager
                            coroutineScope.launch {
                                pagerState.animateScrollToPage(
                                    page = item.ordinal,
                                    animationSpec = tween(300),
                                )
                            }
                        },
                    )
                }
            }
        },
        navigationIcon = {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    painter =
                        painterResource(
                            id = BaseR.drawable.bas_arrow_back,
                        ),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                )
            }
        },
        actions = {
            IconButton(onClick = { /* Handle share */ }) {
                Icon(Icons.Default.Share, contentDescription = "Share")
            }
            IconButton(onClick = { /* Handle more */ }) {
                Icon(Icons.Default.MoreVert, contentDescription = "More")
            }
        },
        scrollBehavior = scrollBehavior,
    ) // end of CenterAlignedTopAppBar
}

@Composable
fun CommentTabContent(songInfo: Screen.Comment) {
    Column(
        modifier =
            Modifier
                .fillMaxSize(),
        // .background(Color.White)
    ) {
        SongInfoHeader(songInfo)
        HorizontalDivider(thickness = 8.dp, color = Color(0xFFF5F5F5))
        FilterTabs()
        // Comments List
        LazyColumn(
            modifier =
                Modifier
                    .fillMaxSize(),
            // .background(Color.White)
        ) {
            items(
                items = LocalCommentData.mockComments,
                key = { comment -> comment.id },
                contentType = { "comment" },
            ) { comment ->
                CommentItem(comment = comment, onLike = { })
            }
        }
    }
}

@Composable
fun NotesTabContent() {
    Box(
        modifier =
            Modifier
                .fillMaxSize()
                .background(Color.White),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = "笔记内容",
            fontSize = 16.sp,
            color = Color.Gray,
        )
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewCommentScreen() {
    PreviewWrapper {
        val viewModel: CommentViewModel =
            viewModel(
                factory =
                    viewModelProviderFactoryOf {
                        CommentViewModel(
                            savedStateHandle =
                                SavedStateHandle().also {
                                    it["songId"] = 10712L
                                    it["songName"] = "甜蜜蜜"
                                    it["artist"] = "鄧麗君"
                                },
                            uiEventManager = UiEventManager(),
                        )
                    },
            )

        CommentMainScreen(
            // widthSize = WindowWidthSizeClass.Compact,
            // songInfo = LocalCommentData.previewSongInfo,
            viewModel = viewModel,
        )
    }
}
