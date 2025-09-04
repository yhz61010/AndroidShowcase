package com.leovp.discovery.presentation.comment

import android.content.res.Configuration
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import com.leovp.compose.composable.event.UiEventManager
import com.leovp.discovery.domain.model.SongModel
import com.leovp.discovery.testdata.PreviewDiscoveryModule
import com.leovp.discovery.testdata.local.datasource.LocalCommentData
import com.leovp.feature.base.event.composable.GenericEventHandler
import com.leovp.feature.base.ui.CommentBottomNavigationItems
import com.leovp.feature.base.ui.LocalNavigationActions
import com.leovp.feature.base.ui.PreviewWrapper
import com.leovp.mvvm.viewmodel.viewModelProviderFactoryOf
import kotlinx.coroutines.launch
import com.leovp.feature.base.R as BaseR

/**
 * Author: Michael Leo
 * Date: 2025/8/26 13:14
 */

private val imgSize = 40.dp
private val imgPadding = 8.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommentMainScreen(
    songInfo: SongModel,
    viewModel: CommentViewModel = hiltViewModel<CommentViewModel>(),
) {
    // val context = LocalContext.current
    val navController = LocalNavigationActions.current
    GenericEventHandler(
        events = viewModel.requireUiEvents,
        navController = navController,
    )

    val coroutineScope = rememberCoroutineScope()
    val pagerScreenValues = CommentBottomNavigationItems.entries.toTypedArray()
    val pagerState =
        rememberPagerState(
            initialPage = CommentBottomNavigationItems.COMMENT.ordinal,
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
            CenterAlignedTopAppBar(
                // colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                //     containerColor = Color.Cyan
                // ),
                // WindowInsets.waterfall // WindowInsets.displayCutout // or all 0.dp
                // windowInsets = WindowInsets(0.dp, 0.dp, 0.dp, 0.dp),
                title = {
                    TabRow(
                        selectedTabIndex = pagerState.currentPage,
                        modifier = Modifier.fillMaxWidth(),
                        // containerColor = Color.White,
                        contentColor = Color.Black,
                        divider = {},
                        indicator = { tabPositions ->
                            TabRowDefaults.PrimaryIndicator(
                                modifier = Modifier.tabIndicatorOffset(tabPositions[pagerState.currentPage]),
                                height = 3.dp,
                                color = MaterialTheme.colorScheme.primary,
                                shape = RoundedCornerShape(2.dp),
                            )
                        }
                    ) {
                        pagerScreenValues.forEachIndexed { index, item ->
                            Tab(
                                text = {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Text(stringResource(pagerScreenValues[index].screen.nameResId))
                                    }
                                },
                                selected = pagerState.currentPage == index,
                                onClick = {
                                    /* Will be handled by pager */
                                    coroutineScope.launch {
                                        pagerState.animateScrollToPage(
                                            page = item.ordinal,
                                            animationSpec = tween(300),
                                        )
                                    }
                                }
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
        }, // end of topBar
    ) { contentPadding ->
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.padding(contentPadding),
        ) { page ->
            when (page) {
                0 -> CommentTabContent(songInfo)
                1 -> NotesTabContent()
            }
        }
    }
}

@Composable
fun CommentTabContent(songInfo: SongModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
        // .background(Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = "https://picsum.photos/40/40?random=0",
                contentDescription = "Avatar",
                modifier = Modifier
                    .size(imgSize)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(imgPadding))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = songInfo.name,
                    style = MaterialTheme.typography.titleMedium,
                )
                Text(
                    text = " - ${songInfo.getSongArtist()}",
                    color = Color.Gray,
                    style = MaterialTheme.typography.titleSmall,
                )
            }
        }
        HorizontalDivider(thickness = 8.dp, color = Color(0xFFF5F5F5))
        // Filter Tabs
        Row(
            modifier = Modifier
                .fillMaxWidth()
                // .background(Color.White)
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "评论(106486)",
                style = MaterialTheme.typography.titleMedium
            )
            FilterChip(
                onClick = { },
                label = { Text("推荐") },
                selected = true
            )
            FilterChip(
                onClick = { },
                label = { Text("最热") },
                selected = false
            )
            FilterChip(
                onClick = { },
                label = { Text("最新") },
                selected = false
            )
        }

        // Comments List
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
            // .background(Color.White)
        ) {
            items(
                items = LocalCommentData.mockComments,
                key = { comment -> comment.id }
            ) { comment ->
                CommentItem(comment = comment, onLike = { })
            }
        }
    }
}

@Composable
fun CommentItem(
    comment: SongModel.Comment,
    onLike: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, top = 0.dp, bottom = 22.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Avatar
                    AsyncImage(
                        model = comment.userAvatar,
                        contentDescription = "User Avatar",
                        modifier = Modifier
                            .size(imgSize)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )
                    Spacer(modifier = Modifier.width(imgPadding))
                    // User information
                    Column {
                        // User name and mark
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = comment.userName,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = Color.Black
                                )

                                if (comment.isVip) {
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Card(
                                        shape = RoundedCornerShape(2.dp),
                                        colors = CardDefaults.cardColors(containerColor = Color.Red),
                                        modifier = Modifier.height(16.dp)
                                    ) {
                                        Text(
                                            text = "VIP",
                                            color = Color.White,
                                            fontSize = 10.sp,
                                            modifier = Modifier.padding(
                                                horizontal = 4.dp,
                                                vertical = 1.dp
                                            )
                                        )
                                    }
                                }
                            }
                        } // End of Row - User name and mark
                        // Date/Location
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = comment.timeAgo,
                                style = MaterialTheme.typography.bodySmall,
                                color = Color.Gray
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = comment.location,
                                style = MaterialTheme.typography.bodySmall,
                                color = Color.Gray
                            )
                        } // End of Row - Date/Location
                    } // End of Column - User information
                    Spacer(modifier = Modifier.weight(1f))
                    // Action Row (Like button)
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = comment.likeCount.toString(),
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.Gray
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        IconButton(
                            onClick = onLike,
                            modifier = Modifier.size(20.dp)
                        ) {
                            Icon(
                                Icons.Default.ThumbUp,
                                contentDescription = "Like",
                                tint = if (comment.isLiked) Color.Red else Color.Gray,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    } // End of Row - Action Row (Like button)
                } // End of Row - User title Row

                Spacer(modifier = Modifier.height(4.dp))

                // Comment text
                Text(
                    modifier = Modifier.padding(start = imgSize + imgPadding),
                    text = comment.comment,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        lineHeight = 20.sp
                    ),
                    color = Color.Black,
                )

                // Show more
                if (comment.replyCount > 0) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier
                            .padding(start = imgSize + imgPadding)
                            .clickable(onClick = {}),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        HorizontalDivider(
                            modifier = Modifier.width(32.dp),
                            thickness = 1.dp,
                            color = Color.LightGray,
                        )
                        Text(
                            modifier = Modifier.padding(start = 4.dp),
                            text = "展开${comment.replyCount}条回复",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color(0xFF2A7E87)
                        )
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowDown,
                            contentDescription = "ArrowForward",
                            tint = Color(0xFF2A7E87)
                        )
                    }
                } // End of if - Show more
            }
        }
    }
}

@Composable
fun NotesTabContent() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "笔记内容",
            fontSize = 16.sp,
            color = Color.Gray
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
                            PreviewDiscoveryModule.previewDiscoveryListUseCase,
                            UiEventManager(),
                        )
                    },
            )

        CommentMainScreen(
            // widthSize = WindowWidthSizeClass.Compact,
            songInfo = LocalCommentData.previewSongInfo,
            viewModel = viewModel,
        )
    }
}
