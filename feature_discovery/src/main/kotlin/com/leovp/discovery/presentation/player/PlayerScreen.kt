package com.leovp.discovery.presentation.player

import android.content.res.Configuration
import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.leovp.android.exts.toast
import com.leovp.compose.utils.previewInitLog
import com.leovp.discovery.R
import com.leovp.discovery.domain.model.SongModel
import com.leovp.discovery.presentation.player.composable.SeekbarItem
import com.leovp.discovery.presentation.player.composable.TrackBadge
import com.leovp.discovery.testdata.PreviewPlayerModule
import com.leovp.discovery.ui.theme.mark_vip_bg2
import com.leovp.log.base.d
import com.leovp.mvvm.viewmodel.viewModelProviderFactoryOf
import com.leovp.ui.theme.ImmersiveTheme

/**
 * Author: Michael Leo
 * Date: 2024/8/21 13:48
 */
private const val TAG = "PlayerScreen"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlayerScreen(
    onMenuUpAction: () -> Unit,
    onShareAction: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: PlayerViewModel = hiltViewModel<PlayerViewModel>(),
) {
    SideEffect {
        d(TAG) {
            "=> Enter PlayerScreen <= ${viewModel.songArtist}-${viewModel.songTrack} id=${viewModel.songId}"
        }
    }
    LaunchedEffect(Unit) {
        // d(TAG) { "=> Enter PlayerScreen <= -> LaunchedEffect" }
        viewModel.onEnter()
    }

    val uiStateFlow by viewModel.uiStateFlow.collectAsStateWithLifecycle()
    val uiState = uiStateFlow as PlayerViewModel.UiState.Content
    uiState.exception?.let { resultException ->
        val message = resultException.cause?.cause?.message ?: resultException.message
        LocalContext.current.toast(message, error = true, longDuration = true)
    }

    val topAppBarState = rememberTopAppBarState()
    // val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(topAppBarState)
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(topAppBarState)
    val containerBg = MaterialTheme.colorScheme.primary
    Scaffold(
        // contentWindowInsets = WindowInsets(0.dp, 0.dp, 0.dp, 0.dp),
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        containerColor = containerBg,
        topBar = {
            CenterAlignedTopAppBar(
                // colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                //     containerColor = Color.Cyan
                // ),
                // WindowInsets.waterfall // WindowInsets.displayCutout // or all 0.dp
                // windowInsets = WindowInsets(0.dp, 0.dp, 0.dp, 0.dp),
                title = {
                    TitleContent(
                        uiState = uiState,
                        defArtist = viewModel.songArtist,
                        defTrack = viewModel.songTrack,
                    )
                },
                colors =
                    TopAppBarDefaults.centerAlignedTopAppBarColors().copy(
                        containerColor = containerBg,
                    ),
                navigationIcon = {
                    IconButton(onClick = onMenuUpAction) {
                        Icon(
                            modifier = Modifier.requiredSize(32.dp),
                            imageVector = Icons.Default.KeyboardArrowDown,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onPrimary,
                        )
                    }
                },
                actions = {
                    IconButton(onClick = onShareAction) {
                        Icon(
                            painter =
                                painterResource(
                                    id = R.drawable.dis_share_circle_fill,
                                ),
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onPrimary,
                        )
                    }
                },
                scrollBehavior = scrollBehavior,
            ) // end of CenterAlignedTopAppBar
        }, // end of topBar
    ) { contentPadding ->
        PlayerScreenContent(
            viewModel = viewModel,
            modifier =
                Modifier
                    // .nestedScroll(scrollBehavior.nestedScrollConnection)
                    // innerPadding takes into account the top and bottom bar
                    .padding(contentPadding),
        )
    }
}

@Composable
fun TitleContent(
    uiState: PlayerViewModel.UiState.Content,
    defArtist: String,
    defTrack: String,
) {
    val songTitle =
        uiState.songInfo?.getSongFullName(
            defArtist = defArtist,
            defTrack = defTrack,
        ) ?: ""
    d(TAG) { "AppBar title=$songTitle" }

    Text(
        text = songTitle,
        color = MaterialTheme.colorScheme.onPrimary,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
    )
}

@Composable
fun CommentItem(
    commentsData: SongModel.Comment,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val inlineMoreContentId = "more"
    val text =
        buildAnnotatedString {
            append(commentsData.comment)
            appendInlineContent(inlineMoreContentId, "[more]")
        }
    val inlineContent =
        mapOf(
            inlineMoreContentId to
                InlineTextContent(
                    placeholder =
                        Placeholder(
                            width = 22.sp,
                            height = 22.sp,
                            placeholderVerticalAlign = PlaceholderVerticalAlign.Center,
                        ),
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                        contentDescription = null,
                        modifier = Modifier.size(22.dp),
                        tint = MaterialTheme.colorScheme.onPrimary,
                    )
                },
        )
    Row(
        modifier =
            modifier
                .fillMaxWidth()
                .padding(16.dp, 0.dp),
        horizontalArrangement = Arrangement.Center,
    ) {
        Text(
            text = text,
            inlineContent = inlineContent,
            modifier =
                Modifier
                    .background(
                        color = mark_vip_bg2,
                        shape = MaterialTheme.shapes.extraLarge,
                    )
                    .clickable(onClick = onClick)
                    .padding(horizontal = 12.dp, vertical = 4.dp)
                    .alpha(0.6f),
            color = MaterialTheme.colorScheme.onPrimary,
            style =
                MaterialTheme.typography.titleSmall.copy(
                    fontWeight = FontWeight.Black,
                ),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
    }
}

@Composable
fun TrackArtistItem(
    songInfo: SongModel?,
    artist: String,
    track: String,
    onEvent: (PlayerUiEvent.SongEvent) -> Unit,
) {
    val markText = songInfo?.markText
    val favoriteCount = songInfo?.getSongRedCount() ?: 0
    val favoriteCountStr = songInfo?.getSongRedCountStr()
    val commentCount = songInfo?.getSongCommentCount() ?: 0
    Row(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(16.dp, 0.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        TrackInfoItem(markText = markText, artist = artist, track = track, onEvent = onEvent)
        Row(
            modifier = Modifier.weight(0.45f),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            if (favoriteCount > 0) {
                TrackBadge(
                    id = R.drawable.dis_favorite_24px,
                    count = favoriteCount,
                    onClick = { onEvent(PlayerUiEvent.SongEvent.FavoriteClick) },
                    countStr = favoriteCountStr,
                )
            }
            if (commentCount > 0) {
                Spacer(modifier = Modifier.width(28.dp))
                TrackBadge(
                    id = R.drawable.dis_chat_24px,
                    count = commentCount,
                    onClick = { onEvent(PlayerUiEvent.SongEvent.CommentClick) },
                )
            }
        }
    } // end of music title row
}

@Composable
fun RowScope.TrackInfoItem(
    markText: String?,
    artist: String,
    track: String,
    onEvent: (PlayerUiEvent.SongEvent) -> Unit,
) {
    Column(modifier = Modifier.weight(0.55f)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            val smallRounded = MaterialTheme.shapes.small
            Text(
                text = track,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            markText?.let {
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    modifier =
                        Modifier
                            .background(
                                color = mark_vip_bg2,
                                shape = smallRounded,
                            )
                            .padding(horizontal = 4.dp, vertical = 0.dp)
                            .alpha(0.6f),
                    text = it,
                    color = MaterialTheme.colorScheme.onPrimary,
                    style = MaterialTheme.typography.labelSmall,
                    fontSize = 8.0.sp,
                    fontWeight = FontWeight.Black,
                )
            }
        }
        Spacer(modifier = Modifier.height(2.dp))
        Row(
            modifier =
                Modifier
                    .alpha(0.8f)
                    .clickable(onClick = { onEvent(PlayerUiEvent.SongEvent.ArtistClick) }),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                modifier = Modifier.wrapContentSize(),
                text = artist,
                textAlign = TextAlign.Center,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            Spacer(modifier = Modifier.width(6.dp))
            Icon(
                modifier =
                    Modifier
                        .align(alignment = Alignment.CenterVertically)
                        .padding(
                            start = 0.dp,
                            top = 2.dp,
                            end = 0.dp,
                            bottom = 0.dp,
                        ),
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onPrimary,
            )
        }
    } // end of track and artist column
}

@Composable
fun ControllerIconButton(
    @DrawableRes resId: Int,
    onClick: () -> Unit,
    size: Dp = 32.dp,
) {
    IconButton(onClick = onClick) {
        Icon(
            modifier = Modifier.requiredSize(size),
            painter = painterResource(resId),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onPrimary,
        )
    }
}

@Composable
fun ControllerItem(
    onRepeatClick: () -> Unit,
    onBackwardClick: () -> Unit,
    onPlayPauseClick: () -> Unit,
    onForwardClick: () -> Unit,
    onPlaylistClick: () -> Unit,
) {
    Row(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(8.dp, 8.dp)
                .alpha(0.9f),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        ControllerIconButton(R.drawable.dis_refresh_24px, onRepeatClick)
        ControllerIconButton(R.drawable.dis_skip_previous_24px, onBackwardClick)
        ControllerIconButton(
            R.drawable.dis_play_arrow_24px,
            onPlayPauseClick,
            58.dp,
        )
        ControllerIconButton(R.drawable.dis_skip_next_24px, onForwardClick)
        ControllerIconButton(R.drawable.dis_queue_music_24px, onPlaylistClick)
    }
}

@Composable
fun ExtraControllerItem(
    onMirrorClick: () -> Unit,
    onDownloadClick: () -> Unit,
    onInfoClick: () -> Unit,
) {
    val size = 28.dp
    Row(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(8.dp, 0.dp, 8.dp, 0.dp)
                .alpha(0.4f),
        horizontalArrangement = Arrangement.SpaceEvenly,
    ) {
        ControllerIconButton(
            R.drawable.dis_music_cast_24px,
            onMirrorClick,
            size,
        )
        ControllerIconButton(
            R.drawable.dis_download_24px,
            onDownloadClick,
            size,
        )
        ControllerIconButton(R.drawable.dis_more_horiz_24px, onInfoClick, size)
    }
}

@Composable
fun PlayerScreenContent(
    viewModel: PlayerViewModel,
    modifier: Modifier = Modifier,
) {
    val ctx = LocalContext.current
    val uiState by viewModel.uiStateFlow.collectAsStateWithLifecycle()
    val songInfo: SongModel? = (uiState as PlayerViewModel.UiState.Content).songInfo
    SideEffect {
        d(TAG) { "=> Enter PlayerScreenContent <=  songInfo=${songInfo?.name}" }
        d(TAG) { "duration=${songInfo?.duration}" }
    }
    Box(
        // contentPadding = PaddingValues(16.dp),
        modifier = modifier.fillMaxSize(),
    ) {
        CoverAndHotCommentContent(songInfo) {
            ctx.toast("You click on Hot Comment.")
            viewModel.onEvent(PlayerUiEvent.SongEvent.HotCommentClick)
        }
        Column(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(8.dp, 0.dp)
                    .align(Alignment.BottomCenter),
        ) {
            TrackArtistItem(
                songInfo = songInfo,
                artist = viewModel.songArtist,
                track = viewModel.songTrack,
                onEvent = { event ->
                    ctx.toast("You click on $event.")
                    viewModel.onEvent(event)
                },
            )
            Spacer(modifier = Modifier.height(10.dp))
            SeekbarItem(
                positionState = viewModel.playPositionState,
                duration = songInfo?.duration?.toFloat() ?: 0f,
                quality = songInfo?.quality ?: SongModel.Quality.Standard,
                onPositionChange = { pos -> viewModel.updatePlayPos(pos) },
                onQualityClick = {
                    ctx.toast("You click on Quality.")
                    viewModel.onEvent(PlayerUiEvent.PlayerEvent.QualityClick)
                },
            )
            ControllerContent(viewModel)
        } // End of Song info main column
    } // End of root container
}

@Composable
fun ControllerContent(viewModel: PlayerViewModel) {
    val ctx = LocalContext.current
    ControllerItem(
        onRepeatClick = {
            ctx.toast("You click on Repeat.")
            viewModel.onEvent(PlayerUiEvent.PlayerEvent.RepeatClick)
        },
        onBackwardClick = {
            ctx.toast("You click on Backward.")
            viewModel.onEvent(PlayerUiEvent.PlayerEvent.BackwardClick)
        },
        onPlayPauseClick = {
            ctx.toast("You click on Play/Pause.")
            viewModel.onEvent(PlayerUiEvent.PlayerEvent.PlayPauseClick)
        },
        onForwardClick = {
            ctx.toast("You click on Forward.")
            viewModel.onEvent(PlayerUiEvent.PlayerEvent.ForwardClick)
        },
        onPlaylistClick = {
            ctx.toast("You click on Playlist.")
            viewModel.onEvent(PlayerUiEvent.PlayerEvent.PlaylistClick)
        },
    )
    ExtraControllerItem(
        onMirrorClick = {
            ctx.toast("You click on Mirror.")
            viewModel.onEvent(PlayerUiEvent.ExtraEvent.MirrorClick)
        },
        onDownloadClick = {
            ctx.toast("You click on Download.")
            viewModel.onEvent(PlayerUiEvent.ExtraEvent.DownloadClick)
        },
        onInfoClick = {
            ctx.toast("You click on Information.")
            viewModel.onEvent(PlayerUiEvent.ExtraEvent.InfoClick)
        },
    ) // End of ExtraControllerItem
}

@Composable
fun CoverAndHotCommentContent(
    songInfo: SongModel?,
    onHotCommentClick: () -> Unit,
) {
    val ctx = LocalContext.current
    Column(
        modifier =
            Modifier
                .fillMaxWidth()
                .height(360.dp),
    ) {
        Box {
            AsyncImage(
                model =
                    ImageRequest
                        .Builder(ctx)
                        .data(
                            songInfo?.album?.getAlbumCoverUrl(),
                        ).crossfade(true)
                        .build(),
                contentDescription = null,
                placeholder =
                    ColorPainter(
                        MaterialTheme.colorScheme.onPrimaryContainer,
                    ),
                contentScale = ContentScale.Fit,
                modifier = Modifier.fillMaxSize(),
                alignment = Alignment.TopCenter,
            )
            val commentsData = songInfo?.commentsModel
            val commentList: List<SongModel.Comment> =
                commentsData?.hotComments ?: emptyList()
            if (commentList.isNotEmpty()) {
                CommentItem(
                    commentsData = commentList.first(),
                    onClick = {
                        ctx.toast("You click on Hot Comment.")
                        onHotCommentClick()
                    },
                    modifier =
                        Modifier
                            .align(Alignment.BottomCenter)
                            .padding(0.dp, 10.dp),
                )
            }
        }
    }
}

@Preview(name = "Daylight")
@Preview(name = "Night", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewPlayerScreen() {
    previewInitLog()

    val viewModel: PlayerViewModel =
        viewModel(
            factory =
                viewModelProviderFactoryOf {
                    PlayerViewModel(
                        savedStateHandle =
                            SavedStateHandle().also {
                                it["id"] = arrayOf(10712L)
                                it["artist"] = "鄧麗君"
                                it["track"] = "甜蜜蜜"
                            },
                        useCase = PreviewPlayerModule.previewPlayerUseCase,
                        songEventDelegate = SongEventDelegate(),
                        playerDelegate = PlayerDelegate(),
                        playerExtraDelegate = PlayerExtraDelegate(),
                    )
                },
        )

    viewModel.updatePlayPos(80_000f)
    ImmersiveTheme(
        systemBarColor = Color.Transparent,
        dynamicColor = false,
        lightSystemBar = false,
    ) {
        PlayerScreen(
            onMenuUpAction = {},
            onShareAction = {},
            viewModel = viewModel,
        )
    }
}
