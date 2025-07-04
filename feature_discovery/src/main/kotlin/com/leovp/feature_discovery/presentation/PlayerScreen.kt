package com.leovp.feature_discovery.presentation

import android.content.res.Configuration
import androidx.annotation.DrawableRes
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
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Badge
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.leovp.android.exts.toast
import com.leovp.feature_discovery.R
import com.leovp.feature_discovery.domain.model.SongModel
import com.leovp.feature_discovery.testdata.PreviewPlayerModule
import com.leovp.feature_discovery.ui.theme.mark_vip_bg2
import com.leovp.feature_discovery.ui.theme.place_holder_bg_color
import com.leovp.json.toJsonString
import com.leovp.module.common.exception.ApiException
import com.leovp.module.common.log.d
import com.leovp.module.common.presentation.viewmodel.viewModelProviderFactoryOf
import com.leovp.module.common.utils.formatTimestampShort
import com.leovp.module.common.utils.previewInitLog
import com.leovp.module.common.utils.toCounterBadgeText
import com.leovp.ui.theme.ImmersiveTheme
import com.smarttoolfactory.slider.ColorfulSlider
import com.smarttoolfactory.slider.MaterialSliderDefaults
import com.smarttoolfactory.slider.SliderBrushColor
import kotlinx.coroutines.flow.StateFlow

/**
 * Author: Michael Leo
 * Date: 2024/8/21 13:48
 */
private const val TAG = "PlayerScreen"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlayerScreen(
    viewModel: PlayerViewModel,
    ids: Array<Long>,
    artist: String,
    track: String,
    onMenuUpAction: () -> Unit,
    onShareAction: () -> Unit,
    modifier: Modifier = Modifier,
) {
    SideEffect {
        d(TAG) { "=> Enter PlayerScreen <=  loading=${viewModel.loading}  $artist-$track  ids=${ids.toJsonString()}" }
    }
    val ctx = LocalContext.current

    LaunchedEffect(Unit) {
        // d(TAG) { "=> Enter PlayerScreen <= -> LaunchedEffect" }
        viewModel.getData(ids = ids)
    }

    val uiStateFlow = viewModel.uiState.collectAsStateWithLifecycle()
    val uiState = uiStateFlow.value
    uiState.exception?.let {
        val apiException = it as ApiException
        val code = apiException.code
        val message = apiException.message
        ctx.toast("$code: $message", error = true, longDuration = true)
    }

    // val context = LocalContext.current
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
                    Text(
                        text = uiState.getSongFullName(
                            defArtist = artist,
                            defTrack = track
                        ),
                        color = MaterialTheme.colorScheme.onPrimary,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors().copy(
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
                            painter = painterResource(
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
            artist = artist,
            track = track,
            modifier = Modifier
                // .nestedScroll(scrollBehavior.nestedScrollConnection)
                // innerPadding takes into account the top and bottom bar
                .padding(contentPadding),
        )
    }
}

@Composable
fun TrackBadge(
    @DrawableRes id: Int,
    count: Long,
    onClick: () -> Unit,
    countStr: String? = null
) {
    Box(modifier = Modifier.clickable(onClick = onClick)) {
        Icon(
            painter = painterResource(id = id),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onPrimary,
        )
        Badge(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(20.dp, 0.dp, 0.dp, 0.dp),
            containerColor = Color.Transparent,
            contentColor = MaterialTheme.colorScheme.onPrimary
        ) {
            Text(
                modifier = Modifier.background(Color.Transparent),
                text = countStr ?: count.toCounterBadgeText(9999),
            )
        }
    }
}

@Composable
fun CommentItem(commentsData: SongModel.Comment, onClick: () -> Unit) {
    val inlineMoreContentId = "more"
    val text = buildAnnotatedString {
        append(commentsData.comment)
        appendInlineContent(inlineMoreContentId, "[more]")
    }
    val inlineContent = mapOf(
        inlineMoreContentId to InlineTextContent(
            placeholder = Placeholder(
                width = 22.sp,
                height = 22.sp,
                placeholderVerticalAlign = PlaceholderVerticalAlign.Center
            )
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = null,
                modifier = Modifier.size(22.dp),
                tint = MaterialTheme.colorScheme.onPrimary
            )
        }
    )
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp, 0.dp),
        horizontalArrangement = Arrangement.Center,
    ) {
        Text(
            text = text,
            inlineContent = inlineContent,
            modifier = Modifier
                .background(
                    color = mark_vip_bg2,
                    shape = MaterialTheme.shapes.extraLarge,
                )
                .clickable(onClick = onClick)
                .padding(horizontal = 12.dp, vertical = 4.dp)
                .alpha(0.6f),
            color = MaterialTheme.colorScheme.onPrimary,
            style = MaterialTheme.typography.titleSmall.copy(
                fontWeight = FontWeight.Black
            ),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
    }
}

@Composable
fun TrackArtistItem(
    artist: String,
    track: String,
    markText: String? = null,
    favoriteCount: Long = 0,
    favoriteCountStr: String? = null,
    commentCount: Long = 0,
    onArtistClick: () -> Unit,
    onFavoriteClick: () -> Unit,
    onCommentClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp, 0.dp),
        verticalAlignment = Alignment.CenterVertically,
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
                markText?.let { it ->
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        modifier = Modifier
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
                modifier = Modifier
                    .alpha(0.8f)
                    .clickable(onClick = onArtistClick),
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
                    modifier = Modifier
                        .align(alignment = Alignment.CenterVertically)
                        .padding(start = 0.dp, top = 2.dp, end = 0.dp, bottom = 0.dp),
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimary,
                )
            }
        } // end of track and artist column
        Row(
            modifier = Modifier.weight(0.45f),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            if (favoriteCount > 0) {
                TrackBadge(
                    id = R.drawable.dis_favorite_24px,
                    count = favoriteCount,
                    onClick = onFavoriteClick,
                    countStr = favoriteCountStr,
                )
            }
            if (commentCount > 0) {
                Spacer(modifier = Modifier.width(28.dp))
                TrackBadge(
                    id = R.drawable.dis_chat_24px,
                    count = commentCount,
                    onClick = onCommentClick,
                )
            }
        }
    } // end of music title row
}

@Composable
fun DurationItem(text: String, onClick: () -> Unit = {}) {
    Text(
        modifier = Modifier.clickable { onClick() },
        text = text,
        color = MaterialTheme.colorScheme.onPrimary,
        style = MaterialTheme.typography.labelMedium,
    )
}

/**
 * @param positionState The value of the slider in milliseconds.
 * @param duration The duration in milliseconds.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SeekbarItem(
    positionState: StateFlow<Float>,
    duration: Float,
    quality: SongModel.Quality,
    onPositionChange: (Float) -> Unit,
    onQualityClick: () -> Unit,
) {
    val ctx = LocalContext.current
    val qualityIdx = quality.ordinal
    val qualityName = ctx.resources.getStringArray(R.array.dis_player_song_quality_name)[qualityIdx]
    val position = positionState.collectAsStateWithLifecycle().value

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp, 0.dp)
    ) {
        // SideEffect {
        //     d(TAG) { "=> Enter SeekbarItem <=  quality=$quality  duration=$duration  position=$position" }
        // }
        ColorfulSlider(
            value = position,
            onValueChange = onPositionChange,
            modifier = Modifier
                .padding(0.dp)
                .semantics { contentDescription = "Seekbar" }
                .fillMaxWidth()
                .requiredHeight(12.dp),
            valueRange = 0f..duration,
            onValueChangeFinished = {
                // launch some business logic update with the state you hold
                // viewModel.updateSelectedSliderValue(sliderPosition)
            },
            trackHeight = 2.dp,
            thumbRadius = 4.dp,
            colors = MaterialSliderDefaults.materialColors(
                thumbColor = SliderBrushColor(MaterialTheme.colorScheme.onPrimary),
                activeTrackColor = SliderBrushColor(
                    MaterialTheme.colorScheme.primaryContainer
                ),
                inactiveTrackColor = SliderBrushColor(
                    MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.55f)
                ),
            ),
        ) // end of Slider
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(2.dp, 0.dp)
                .alpha(0.85f),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            DurationItem(position.toLong().formatTimestampShort())
            DurationItem(text = qualityName, onClick = onQualityClick)
            DurationItem(duration.toLong().formatTimestampShort())
        } // end of duration row
    }
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
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp, 24.dp)
            .alpha(0.9f),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        ControllerIconButton(R.drawable.dis_refresh_24px, onRepeatClick)
        ControllerIconButton(R.drawable.dis_skip_previous_24px, onBackwardClick)
        ControllerIconButton(R.drawable.dis_play_arrow_24px, onPlayPauseClick, 58.dp)
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
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp, 0.dp, 8.dp, 12.dp)
            .alpha(0.4f),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        ControllerIconButton(R.drawable.dis_music_cast_24px, onMirrorClick, size)
        ControllerIconButton(R.drawable.dis_download_24px, onDownloadClick, size)
        ControllerIconButton(R.drawable.dis_more_horiz_24px, onInfoClick, size)
    }
}

@Composable
fun PlayerScreenContent(
    viewModel: PlayerViewModel,
    artist: String,
    track: String,
    modifier: Modifier = Modifier,
) {
    val ctx = LocalContext.current
    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value

    SideEffect {
        d(TAG) { "=> Enter PlayerScreenContent <=  songInfo=${uiState.songInfo?.name}" }
        d(TAG) {
            "artist=$artist  track=$track  ${viewModel.playPosition.value}  " +
                    "duration=${uiState.getSongDuration()}"
        }
    }

    Box(
        // contentPadding = PaddingValues(16.dp),
        modifier = modifier.fillMaxSize(),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(420.dp)
                .align(Alignment.TopCenter),
        ) {
            AsyncImage(
                model = ImageRequest.Builder(ctx)
                    .data(uiState.songInfo?.album?.getAlbumCoverUrl()).crossfade(true)
                    .build(),
                contentDescription = null,
                placeholder = ColorPainter(place_holder_bg_color),
                contentScale = ContentScale.Fit,
                modifier = Modifier.fillMaxSize(),
            )
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp, 0.dp)
                .align(Alignment.BottomCenter)
        ) {
            val commentsData = uiState.songInfo?.commentsModel
            val commentList: List<SongModel.Comment> = commentsData?.hotComments ?: emptyList()
            if (commentList.isNotEmpty()) {
                Spacer(modifier = Modifier.height(12.dp))
                CommentItem(commentList.first()) {
                    ctx.toast("You click on Hot Comment.")
                    viewModel.onHotCommentClick()
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
            TrackArtistItem(
                artist = artist,
                track = track,
                markText = uiState.songInfo?.markText,
                favoriteCount = uiState.getSongRedCount(),
                favoriteCountStr = uiState.getSongRedCountStr(),
                commentCount = uiState.getSongCommentCount(),
                onArtistClick = {
                    ctx.toast("You click on Artist: $artist")
                    viewModel.onArtistClick(artist)
                },
                onFavoriteClick = {
                    ctx.toast("You click on Favorite.")
                    viewModel.onFavoriteClick()
                },
                onCommentClick = {
                    ctx.toast("You click on Comment.")
                    viewModel.onCommentClick()
                },
            )
            Spacer(modifier = Modifier.height(20.dp))
            SeekbarItem(
                positionState = viewModel.playPosition,
                duration = uiState.getSongDuration().toFloat(),
                quality = uiState.getSongQuality(),
                onPositionChange = { pos -> viewModel.updatePlayPos(pos) },
                onQualityClick = {
                    ctx.toast("You click on Quality.")
                    viewModel.onQualityClick()
                }
            )
            ControllerItem(
                onRepeatClick = {
                    ctx.toast("You click on Repeat.")
                    viewModel.onRepeatClick()
                },
                onBackwardClick = {
                    ctx.toast("You click on Backward.")
                    viewModel.onBackwardClick()
                },
                onPlayPauseClick = {
                    ctx.toast("You click on Play/Pause.")
                    viewModel.onPlayPauseClick()
                },
                onForwardClick = {
                    ctx.toast("You click on Forward.")
                    viewModel.onForwardClick()
                },
                onPlaylistClick = {
                    ctx.toast("You click on Playlist.")
                    viewModel.onPlaylistClick()
                },
            )
            ExtraControllerItem(
                onMirrorClick = {
                    ctx.toast("You click on Mirror.")
                    viewModel.onMirrorClick()
                },
                onDownloadClick = {
                    ctx.toast("You click on Download.")
                    viewModel.onDownloadClick()
                },
                onInfoClick = {
                    ctx.toast("You click on Information.")
                    viewModel.onInfoClick()
                }
            ) // End of ExtraControllerItem
        } // End of Song info main column
    } // End of root container
}

@Preview(name = "Daylight")
@Preview(name = "Night", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewPlayerScreen() {
    previewInitLog()

    val viewModel: PlayerViewModel = viewModel(
        factory = viewModelProviderFactoryOf {
            PlayerViewModel(PreviewPlayerModule.previewPlayerUseCase)
        },
    )

    viewModel.updatePlayPos(80_000f)
    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value
    d("PreviewPlayerScreen") { "---> data: ${uiState.songInfo}" }
    ImmersiveTheme(
        systemBarColor = Color.Transparent,
        dynamicColor = false,
        lightSystemBar = false,
    ) {
        PlayerScreen(
            viewModel = viewModel,
            ids = arrayOf(123L),
            artist = "鄧麗君",
            track = "甜蜜蜜",
            onMenuUpAction = {},
            onShareAction = {},
        )
    }
}
