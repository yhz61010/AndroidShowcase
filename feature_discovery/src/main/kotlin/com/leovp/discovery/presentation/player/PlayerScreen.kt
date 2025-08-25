package com.leovp.discovery.presentation.player

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.leovp.android.exts.toast
import com.leovp.compose.utils.previewInitLog
import com.leovp.discovery.R
import com.leovp.discovery.domain.model.SongModel
import com.leovp.discovery.presentation.player.base.ControllerItem
import com.leovp.discovery.presentation.player.base.CoverAndHotCommentContent
import com.leovp.discovery.presentation.player.base.ExtraControllerItem
import com.leovp.discovery.presentation.player.base.PlayerContract.PlayerUiEvent
import com.leovp.discovery.presentation.player.base.PlayerContract.PlayerUiState
import com.leovp.discovery.presentation.player.base.PlayerDelegate
import com.leovp.discovery.presentation.player.base.PlayerExtraDelegate
import com.leovp.discovery.presentation.player.base.SeekbarItem
import com.leovp.discovery.presentation.player.base.SongEventDelegate
import com.leovp.discovery.presentation.player.base.TitleContent
import com.leovp.discovery.presentation.player.base.TrackBadge
import com.leovp.discovery.presentation.player.base.TrackInfoItem
import com.leovp.discovery.testdata.PreviewPlayerModule
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
            "=> Enter PlayerScreen <= " +
                "${viewModel.songArtist}-${viewModel.songTrack} " +
                "id=${viewModel.songId}"
        }
    }
    // EventHandler(events = viewModel.requireUiEvents)

    val uiStateFlow by viewModel.uiStateFlow.collectAsStateWithLifecycle()
    val uiState = uiStateFlow as PlayerUiState.Content
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
fun PlayerScreenContent(
    viewModel: PlayerViewModel,
    modifier: Modifier = Modifier,
) {
    val ctx = LocalContext.current
    val uiState by viewModel.uiStateFlow.collectAsStateWithLifecycle()
    val songInfo: SongModel? = (uiState as PlayerUiState.Content).songInfo
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
        TrackInfoItem(
            markText = markText,
            artist = artist,
            track = track,
            onEvent = onEvent,
        )
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
                        // uiEventManager = UiEventManager(),
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
