package com.leovp.feature_discovery.presentation

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.leovp.android.exts.toast
import com.leovp.feature_discovery.R
import com.leovp.feature_discovery.domain.enum.MarkType
import com.leovp.feature_discovery.domain.model.CarouselItem
import com.leovp.feature_discovery.domain.model.EverydayItem
import com.leovp.feature_discovery.domain.model.MusicItem
import com.leovp.feature_discovery.testdata.FakeDI
import com.leovp.feature_discovery.ui.theme.mark_hot_bg
import com.leovp.feature_discovery.ui.theme.mark_hot_text_color
import com.leovp.feature_discovery.ui.theme.mark_quality_border
import com.leovp.feature_discovery.ui.theme.mark_quality_text_color
import com.leovp.feature_discovery.ui.theme.mark_vip_border
import com.leovp.feature_discovery.ui.theme.mark_vip_text_color
import com.leovp.module.common.presentation.compose.composable.pullrefresh.PullRefreshIndicator
import com.leovp.module.common.presentation.compose.composable.pullrefresh.pullRefresh
import com.leovp.module.common.presentation.compose.composable.pullrefresh.rememberPullRefreshState
import com.leovp.module.common.presentation.viewmodel.viewModelProviderFactoryOf
import com.leovp.module.common.utils.floorMod
import com.leovp.module.common.utils.previewInitLog
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Author: Michael Leo
 * Date: 2023/7/18 15:06
 */

// private const val TAG = "Discovery"

@Composable
fun DiscoveryScreen(
    scrollState: LazyListState,
    onRefresh: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DiscoveryViewModel = viewModel(
        factory = viewModelProviderFactoryOf { DiscoveryViewModel(FakeDI.discoveryListUseCase) },
    ),
) {
    val ctx = LocalContext.current
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val pullRefreshState = rememberPullRefreshState(
        refreshing = uiState.loading,
        onRefresh = {
            viewModel.refreshAll()
            onRefresh()
        },
    )

    Box(
        modifier = Modifier
            .padding(PaddingValues(horizontal = 0.dp, vertical = 6.dp))
            .fillMaxSize()
            .pullRefresh(pullRefreshState),
        contentAlignment = Alignment.TopCenter,
    ) {
        if (!uiState.loading) {
            LazyColumn(
                // contentPadding = PaddingValues(horizontal = 0.dp, vertical = 6.dp),
                modifier = modifier.fillMaxSize(),
                state = scrollState,
            ) {
                item {
                    CarouselHeader(uiState.carouselRecommends) { item ->
                        ctx.toast("Carousel recommend item: $item")
                    }
                }
                item {
                    EverydayRecommendsHeader()
                    EverydayRecommendsContent(uiState.everydayRecommends) { item ->
                        ctx.toast("Everyday recommend item: $item")
                    }
                }
                item {
                    MusicContentHeader()
                }
                items(uiState.personalRecommends) { data ->
                    MusicContentItem(data)
                }
            } // end LazyColumn
        } // end if

        PullRefreshIndicator(
            refreshing = uiState.loading,
            state = pullRefreshState,
            // modifier = Modifier.align(Alignment.TopCenter)
        )
    } // end Box
}

@Composable
fun MusicContentHeader() {
    Row(
        modifier = Modifier.padding(16.dp, 8.dp, 16.dp, 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(R.string.dis_discovery_tab_chinese_curated),
            style = MaterialTheme.typography.bodyLarge.copy(
                fontWeight = FontWeight.Black
            ),
        )
        Spacer(modifier = Modifier.weight(1f))
        IconButton(modifier = Modifier.requiredSize(24.dp), onClick = { }) {
            Icon(
                painter = painterResource(id = R.drawable.dis_more_vert),
                tint = Color.Gray,
                contentDescription = null,
            )
        }
    }
}

@Composable
fun EverydayRecommendsHeader() {
    Row(
        modifier = Modifier.padding(16.dp, 8.dp, 16.dp, 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(R.string.dis_discovery_tab_everyday_recommends),
            style = MaterialTheme.typography.bodyLarge.copy(
                fontWeight = FontWeight.Black
            ),
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            modifier = Modifier.weight(1f),
            text = stringResource(com.leovp.module.common.R.string.cmn_listeners, 8939187),
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray,
            fontWeight = FontWeight.Bold
        )
        IconButton(modifier = Modifier.requiredSize(24.dp), onClick = { }) {
            Icon(
                painter = painterResource(id = R.drawable.dis_more_vert),
                tint = Color.Gray,
                contentDescription = null,
            )
        }
    }
}

@Composable
fun EverydayRecommendsContent(
    list: List<EverydayItem>, onItemClick: (EverydayItem) -> Unit
) {
    val cardWidth = 120.dp

    LazyRow(
        contentPadding = PaddingValues(16.dp, 8.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier.fillMaxWidth(),
    ) {
        items(list) { data ->
            Column {
                Card(modifier = Modifier
                    .clickable { onItemClick(data) }
                    .size(cardWidth),
                     shape = MaterialTheme.shapes.large) {
                    Box {
                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(data.getDefaultImageUrl()).crossfade(true).build(),
                            contentDescription = null,
                            placeholder = ColorPainter(Color.LightGray),
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize(),
                        )
                        Row(
                            modifier = Modifier.padding(start = 8.dp, top = 5.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            data.icon?.let { icon ->
                                Icon(
                                    modifier = Modifier
                                        .padding(end = 2.dp)
                                        .size(16.dp),
                                    imageVector = icon,
                                    tint = Color.White,
                                    contentDescription = null,
                                )
                            }
                            Text(
                                text = data.type,
                                color = Color.White,
                                textAlign = TextAlign.Center,
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    fontWeight = FontWeight.Black
                                )
                            )
                        }
                    }
                }
                Text(
                    modifier = Modifier
                        .requiredWidth(cardWidth)
                        .padding(top = 6.dp),
                    style = MaterialTheme.typography.labelMedium,
                    text = data.title,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CarouselHeader(list: List<CarouselItem>, onItemClick: (CarouselItem) -> Unit) {
    val pageCount = list.size
    val pagerState = rememberPagerState(
        initialPage = 0,
        initialPageOffsetFraction = 0f,
        pageCount = { pageCount },
    )

    val pageCountIndex by remember { derivedStateOf { pagerState.currentPage.floorMod(pageCount) } }

    LaunchedEffect(key1 = pagerState.settledPage) {
        launch {
            delay(3000L)
            val target = if (pagerState.currentPage < pageCount - 1) {
                pagerState.currentPage + 1
            } else {
                0
            }

            pagerState.animateScrollToPage(
                page = target,
                animationSpec = tween(
                    durationMillis = 500,
                    easing = FastOutSlowInEasing,
                ),
            )
        }
    }

    Box(modifier = Modifier.padding(bottom = 6.dp)) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxWidth(),
            beyondBoundsPageCount = 1,
            key = { index -> list[index].id },
        ) { page ->
            Card(
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .clickable { onItemClick(list[page]) }
                    .padding(horizontal = 16.dp, vertical = 0.dp)
                    .fillMaxWidth()
                    .heightIn(min = 140.dp),
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current).data(list[page].thumbnail)
                        .crossfade(true).build(),
                    contentDescription = null,
                    placeholder = ColorPainter(Color.LightGray),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize(),
                )
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 26.dp, vertical = 10.dp)
                .align(Alignment.BottomStart),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            repeat(pageCount) { index ->
                Box(
                    modifier = Modifier
                        .padding(start = 2.dp, end = 2.dp)
                        .width(if (index == pageCountIndex) 12.dp else 4.dp)
                        .height(4.dp)
                        .clip(
                            if (index == pageCountIndex) RoundedCornerShape(2.dp) else CircleShape
                        )
                        .background(
                            color = if (index == pageCountIndex) Color.White else Color.LightGray,
                            // shape = if (index == pageCountIndex) {
                            //     RoundedCornerShape(2.dp)
                            // } else {
                            //     CircleShape
                            // }
                        )
                )
            }
        }
    }
}

@Composable
fun MusicContentItem(data: MusicItem) {
    val context = LocalContext.current
    ListItem(
        modifier = Modifier.clickable { context.toast("You clicked item ${data.title}") },
        headlineContent = {
            Text(
                text = data.title,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.secondary,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        supportingContent = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                val smallRounded = MaterialTheme.shapes.small
                val borderWidth = 0.4.dp
                val borderModifier = when (data.type) {
                    MarkType.Hot -> Modifier.border(
                        width = borderWidth, color = mark_hot_bg, shape = smallRounded
                    )

                    MarkType.Special -> Modifier.border(
                        width = borderWidth, color = mark_quality_border, shape = smallRounded
                    )

                    MarkType.Vip -> Modifier.border(
                        width = borderWidth, color = mark_vip_border, shape = smallRounded
                    )
                }
                val backgroundModifier = when (data.type) {
                    MarkType.Hot -> Modifier.background(
                        color = mark_hot_bg, shape = smallRounded
                    )

                    else -> Modifier
                }
                val paddingModifier = when (data.type) {
                    MarkType.Vip -> Modifier.padding(horizontal = 2.dp)
                    MarkType.Hot -> Modifier.padding(horizontal = 4.dp, vertical = 1.dp)
                    else -> Modifier.padding(horizontal = 4.dp)
                }
                val fontFamily = when (data.type) {
                    MarkType.Vip -> FontFamily.SansSerif
                    else -> null
                }
                val fontSize = when (data.type) {
                    MarkType.Vip -> TextUnit(8.0f, TextUnitType.Sp)
                    else -> TextUnit(9.0f, TextUnitType.Sp)
                }
                val textColor = when (data.type) {
                    MarkType.Hot -> mark_hot_text_color
                    MarkType.Special -> mark_quality_text_color
                    MarkType.Vip -> mark_vip_text_color
                }
                if (data.markText.isNotBlank()) {
                    Text(
                        modifier = Modifier
                            .then(borderModifier)
                            .then(backgroundModifier)
                            .then(paddingModifier),
                        text = data.getMarkTextString(
                            stringResource(
                                id = com.leovp.module.common.R.string.cmn_listeners_listening
                            )
                        ),
                        color = textColor,
                        fontSize = fontSize,
                        fontWeight = FontWeight.Black,
                        fontFamily = fontFamily
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                }
                Text(
                    text = data.subTitle,
                    style = MaterialTheme.typography.labelMedium,
                    color = Color.Gray,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        },
        trailingContent = {
            if (data.showTrailIcon) {
                Icon(
                    modifier = Modifier.alpha(0.6f),
                    painter = painterResource(id = R.drawable.dis_smart_display),
                    contentDescription = null,
                    tint = Color.DarkGray
                )
            }
        },
        leadingContent = {
            ListItemImage(
                imageUrl = data.getDefaultImageUrl(),
                contentDescription = null,
                modifier = Modifier.size(56.dp)
            )
        },
    )
}

@Composable
fun ListItemImage(
    imageUrl: String?,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    elevation: Dp = 0.dp
) {
    Surface(
        shape = RoundedCornerShape(8.dp),
        modifier = modifier,
        shadowElevation = elevation,
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current).data(imageUrl).crossfade(true)
                .build(),
            contentDescription = contentDescription,
            placeholder = ColorPainter(Color.LightGray),
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize(),
        )
    }
}

@Preview
@Composable
fun PreviewDiscoveryScreen() {
    previewInitLog()
    DiscoveryScreen(
        scrollState = rememberLazyListState(),
        onRefresh = {},
        viewModel = viewModel(
            factory = viewModelProviderFactoryOf {
                DiscoveryViewModel(FakeDI.previewDiscoveryListUseCase)
            },
        ),
    )
}