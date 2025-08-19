package com.leovp.discovery.presentation.discovery

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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.leovp.android.exts.toast
import com.leovp.compose.composable.pager.DefaultPagerIndicator
import com.leovp.compose.composable.pager.HorizontalAutoPager
import com.leovp.compose.utils.previewInitLog
import com.leovp.discovery.R
import com.leovp.discovery.domain.enum.MarkType
import com.leovp.discovery.domain.model.PlaylistModel
import com.leovp.discovery.domain.model.PrivateContentModel
import com.leovp.discovery.domain.model.TopSongModel
import com.leovp.discovery.presentation.discovery.composable.supportingBackground
import com.leovp.discovery.presentation.discovery.composable.supportingBorder
import com.leovp.discovery.presentation.discovery.composable.supportingPadding
import com.leovp.discovery.testdata.PreviewDiscoveryModule
import com.leovp.discovery.ui.theme.mark_hot_bg
import com.leovp.discovery.ui.theme.mark_hot_text_color
import com.leovp.discovery.ui.theme.mark_quality_text_color
import com.leovp.discovery.ui.theme.mark_vip_text_color
import com.leovp.discovery.ui.theme.place_holder2_bg_color
import com.leovp.discovery.ui.theme.place_holder_bg_color
import com.leovp.discovery.ui.theme.place_holder_err_bg_color
import com.leovp.log.base.d
import com.leovp.log.base.e
import com.leovp.log.base.i
import com.leovp.mvvm.viewmodel.viewModelProviderFactoryOf
import com.leovp.feature.base.R as BaseR

/**
 * Author: Michael Leo
 * Date: 2023/7/18 15:06
 */

private const val TAG = "Discovery"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DiscoveryScreen(
    onRefresh: () -> Unit,
    onPersonalItemClick: (data: TopSongModel) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DiscoveryViewModel = hiltViewModel<DiscoveryViewModel>(),
    listState: LazyListState = rememberLazyListState(),
) {
    val ctx = LocalContext.current
    val uiState =
        viewModel.uiStateFlow.collectAsStateWithLifecycle().value as DiscoveryViewModel.UiState.Content

    SideEffect {
        i(TAG) {
            "=> Enter DiscoveryScreen <=  " +
                "loading=${uiState.isLoading} " +
                "privateContent[${uiState.privateContent.size}] " +
                "recommendPlaylist[${uiState.recommendPlaylist.size}] " +
                "topSong[${uiState.topSongs.size}]"
        }
    }

    uiState.exception?.let { resultException ->
        val message =
            resultException.cause?.cause?.message ?: resultException.message
        e(TAG, throwable = resultException.cause) {
            "DiscoveryScreen -> ResultException"
        }
        ctx.toast(
            msg = "${ctx.getString(BaseR.string.bas_load_failed)}\n$message",
            error = true,
            longDuration = true,
        )
    }

    PullToRefreshBox(
        isRefreshing = uiState.isLoading,
        onRefresh = {
            viewModel.showLoading()
            viewModel.onEnter()
            onRefresh()
        },
        modifier =
            Modifier
                .padding(start = 0.dp, top = 0.dp, end = 0.dp, bottom = 6.dp)
                .fillMaxSize(),
        contentAlignment = Alignment.TopCenter,
    ) {
        DiscoveryContent(
            uiState = uiState,
            listState = listState,
            onPersonalItemClick = onPersonalItemClick,
            modifier = modifier
        )
    } // end PullToRefreshBox
}

@Composable
fun DiscoveryContent(
    uiState: DiscoveryViewModel.UiState.Content,
    listState: LazyListState,
    onPersonalItemClick: (data: TopSongModel) -> Unit,
    modifier: Modifier = Modifier,
) {
    val ctx = LocalContext.current
    LazyColumn(
        // contentPadding = PaddingValues(horizontal = 0.dp, vertical = 6.dp),
        modifier = modifier.fillMaxSize(),
        state = listState,
    ) {
        if (uiState.privateContent.isNotEmpty()) {
            item {
                CarouselContent(
                    uiState.privateContent,
                ) { clickedItem ->
                    ctx.toast(
                        "Carousel recommend clickedItem: $clickedItem",
                    )
                }
            }
        }
        if (uiState.recommendPlaylist.isNotEmpty()) {
            item {
                RecommendsPlaylistHeader()
                RecommendsPlaylistContent(
                    uiState.recommendPlaylist,
                ) { clickedItem ->
                    ctx.toast(
                        "Everyday recommend clickedItem: $clickedItem",
                    )
                }
            }
        }
        if (uiState.topSongs.isNotEmpty()) {
            item {
                TopSongsHeader()
            }
            items(
                items = uiState.topSongs,
                key = { it.id },
            ) { data ->
                TopSongsItem(data, onPersonalItemClick)
            }
        }
    } // end LazyColumn
}

@Composable
fun TopSongsHeader() {
    d(TAG) { "=> Enter PersonalRecommendsHeader <=" }
    Row(
        modifier = Modifier.padding(16.dp, 8.dp, 16.dp, 4.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = stringResource(R.string.dis_discovery_tab_new_songs),
            style =
                MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.Black,
                ),
        )
        Icon(
            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
            contentDescription = null,
            modifier = Modifier.size(24.dp),
        )
        // Spacer(modifier = Modifier.weight(1f))
        // IconButton(modifier = Modifier.requiredSize(24.dp), onClick = { }) {
        //     Icon(
        //         painter = painterResource(id = R.drawable.dis_more_vert),
        //         tint = Color.Gray,
        //         contentDescription = null,
        //     )
        // }
    }
}

@Composable
fun RecommendsPlaylistHeader() {
    d(TAG) { "=> Enter EverydayRecommendsHeader <=" }
    Row(
        modifier =
            Modifier.padding(
                start = 16.dp,
                top = 14.dp,
                end = 16.dp,
                bottom = 4.dp,
            ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text =
                stringResource(
                    R.string.dis_discovery_tab_recommends_playlist,
                ),
            style =
                MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.Black,
                ),
        )
        // Spacer(modifier = Modifier.width(8.dp))
        // Text(
        //     modifier = Modifier.weight(1f),
        //     text = stringResource(com.leovp.module.common.R.string.cmn_listeners, 8939187),
        //     style = MaterialTheme.typography.bodyMedium,
        //     color = Color.Gray,
        //     fontWeight = FontWeight.Bold
        // )
        // IconButton(modifier = Modifier.requiredSize(24.dp), onClick = { }) {
        //     Icon(
        //         painter = painterResource(id = R.drawable.dis_more_vert),
        //         tint = Color.Gray,
        //         contentDescription = null,
        //     )
        // }
    }
}

@Composable
fun RecommendsPlaylistContent(
    list: List<PlaylistModel>,
    onItemClick: (PlaylistModel) -> Unit,
) {
    // d(TAG) { "=> Enter EverydayRecommendsContent <=" }
    val cardWidth = 120.dp

    LazyRow(
        contentPadding = PaddingValues(16.dp, 8.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier.fillMaxWidth(),
    ) {
        items(
            items = list,
            key = { it.id },
        ) { playlist ->
            Column {
                Card(
                    modifier =
                        Modifier
                            .clickable { onItemClick(playlist) }
                            .size(cardWidth),
                    shape = MaterialTheme.shapes.large,
                ) {
                    Box {
                        AsyncImage(
                            model =
                                ImageRequest
                                    .Builder(LocalContext.current)
                                    .data(playlist.getPlayListPicUrl())
                                    .crossfade(true)
                                    .build(),
                            contentDescription = null,
                            placeholder = ColorPainter(place_holder_bg_color),
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize(),
                        )
                        if (playlist.type > 0) {
                            Row(
                                modifier =
                                    Modifier.padding(
                                        start = 8.dp,
                                        top = 5.dp,
                                    ),
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                // topSong.icon?.let { icon ->
                                //     Icon(
                                //         modifier = Modifier
                                //             .padding(end = 2.dp)
                                //             .size(16.dp),
                                //         imageVector = icon,
                                //         tint = Color.White,
                                //         contentDescription = null,
                                //     )
                                // }

                                Text(
                                    text = playlist.name,
                                    color = Color.White,
                                    textAlign = TextAlign.Center,
                                    style =
                                        MaterialTheme.typography.bodyMedium
                                            .copy(
                                                fontWeight = FontWeight.Black,
                                            ),
                                )
                            }
                        }
                    }
                }
                Text(
                    modifier =
                        Modifier
                            .requiredWidth(cardWidth)
                            .padding(top = 6.dp),
                    style = MaterialTheme.typography.labelMedium,
                    text = playlist.name,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )
            }
        }
    }
}

/**
 * Check [HorizontalPagerLoopingIndicatorSample](https://bit.ly/48jS1Fg) for reference.
 */
@Composable
fun CarouselContent(
    list: List<PrivateContentModel>,
    onItemClick: (PrivateContentModel) -> Unit,
) {
    SideEffect { d(TAG) { "=> Enter CarouselContent <=  size=${list.size}" } }
    // The display items count
    val pageCount = list.size
    HorizontalAutoPager(
        modifier = Modifier.fillMaxWidth(),
        pageCount = pageCount,
        indicatorAlignment = Alignment.BottomStart,
        indicatorContent = { index, pageTotalCount ->
            DefaultPagerIndicator(
                currentPageIndex = index,
                pageCount = pageTotalCount,
            )
        },
    ) { index ->
        CarouselItem(currentItem = list[index], onItemClick = onItemClick)
    }
}

@Composable
fun CarouselItem(
    currentItem: PrivateContentModel,
    onItemClick: (PrivateContentModel) -> Unit,
) {
    d(TAG) { "=> Enter CarouselItem <=  image=${currentItem.getThumbPicUrl()}" }
    Card(
        shape = RoundedCornerShape(10.dp),
        colors =
            CardDefaults.cardColors(
                containerColor = place_holder_bg_color,
            ),
        modifier =
            Modifier
                .fillMaxWidth()
                .clickable { onItemClick(currentItem) },
    ) {
        Box {
            AsyncImage(
                model =
                    ImageRequest
                        .Builder(LocalContext.current)
                        .data(currentItem.getThumbPicUrl())
                        .crossfade(true)
                        .build(),
                contentDescription = null,
                placeholder = ColorPainter(place_holder_bg_color),
                contentScale = ContentScale.FillHeight,
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .height(140.dp),
            )
            if (currentItem.typeName.isNotEmpty()) {
                Box(
                    modifier =
                        Modifier
                            .align(Alignment.BottomEnd)
                            .padding(4.dp),
                ) {
                    Text(
                        modifier =
                            Modifier
                                .background(
                                    Color.White,
                                    RoundedCornerShape(4.dp),
                                )
                                .padding(4.dp, 2.dp),
                        text = currentItem.typeName,
                        color = Color.Gray,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Black,
                        fontSize = 10.sp,
                        lineHeight = 12.sp,
                    )
                }
            }
        }
    } // end of Card
}

@Composable
fun TopSongsItem(
    data: TopSongModel,
    onItemClick: (data: TopSongModel) -> Unit,
) {
    // d(TAG) { "=> Enter PersonalRecommendsItem <=" }
    ListItem(
        modifier = Modifier.clickable { onItemClick(data) },
        headlineContent = {
            Text(
                text = data.name,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.secondary,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        },
        supportingContent = {
            SupportingContent(data)
        },
        trailingContent = {
            if (data.showTrailIcon) {
                Icon(
                    modifier = Modifier.alpha(0.6f),
                    imageVector = Icons.Rounded.PlayArrow,
                    contentDescription = null,
                    tint = Color.DarkGray,
                )
            }
        },
        leadingContent = {
            ListItemImage(
                imageUrl = data.getAlbumCoverUrl(),
                contentDescription = null,
                modifier = Modifier.size(56.dp),
            )
        },
    )
}

@Composable
fun SupportingContent(data: TopSongModel) {
    val supportBorderWidth = 0.4.dp
    val supportSmallRounded = MaterialTheme.shapes.small
    val hotModifier = Modifier.border(supportBorderWidth, mark_hot_bg, supportSmallRounded)
    Row(verticalAlignment = Alignment.CenterVertically) {
        val borderModifier = Modifier.supportingBorder(
            data = data,
            defModifier = hotModifier,
            border = supportBorderWidth,
            shape = supportSmallRounded,
        )
        val backgroundModifier = Modifier.supportingBackground(data, supportSmallRounded)
        val paddingModifier = Modifier.supportingPadding(data)
        val textColor = when (data.markType) {
            MarkType.HiRes, MarkType.Hot -> mark_hot_text_color
            MarkType.Special -> mark_quality_text_color
            MarkType.Vip -> mark_vip_text_color
            else -> mark_hot_text_color
        }
        if (data.markType != MarkType.None) {
            Text(
                modifier =
                    Modifier
                        .then(borderModifier)
                        .then(backgroundModifier)
                        .then(paddingModifier),
                text = data.markType.text,
                color = textColor,
                fontSize = when (data.markType) {
                    MarkType.Vip -> 8.sp
                    else -> 9.sp
                },
                fontWeight = FontWeight.Black,
                fontFamily = when (data.markType) {
                    MarkType.HiRes, MarkType.Vip -> FontFamily.SansSerif
                    else -> null
                },
                lineHeight = 14.sp,
            )
            Spacer(modifier = Modifier.width(4.dp))
        }
        Text(
            text = data.getDefaultArtistName(),
            style = MaterialTheme.typography.labelMedium,
            color = Color.Gray,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
    }
}

@Composable
fun ListItemImage(
    imageUrl: String?,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    elevation: Dp = 0.dp,
) {
    d(TAG) { "=> Enter ListItemImage <=  image=$imageUrl" }
    AsyncImage(
        model =
            ImageRequest
                .Builder(
                    LocalContext.current,
                ).data(imageUrl)
                .crossfade(true)
                .build(),
        contentDescription = contentDescription,
        placeholder = ColorPainter(place_holder2_bg_color),
        error = ColorPainter(place_holder_err_bg_color),
        contentScale = ContentScale.Fit,
        // filterQuality = FilterQuality.Low,
        modifier =
            modifier
                .shadow(elevation)
                .clip(RoundedCornerShape(8.dp)),
    )
}

@Preview
@Composable
fun PreviewDiscoveryScreen() {
    previewInitLog()
    val discoveryViewModel: DiscoveryViewModel =
        viewModel(
            factory =
                viewModelProviderFactoryOf {
                    DiscoveryViewModel(
                        PreviewDiscoveryModule.previewDiscoveryListUseCase,
                    )
                },
        )
    DiscoveryScreen(
        listState = rememberLazyListState(),
        viewModel = discoveryViewModel,
        onRefresh = {},
        onPersonalItemClick = {},
    )
}
